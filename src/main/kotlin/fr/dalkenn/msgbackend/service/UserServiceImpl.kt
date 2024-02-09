package fr.dalkenn.msgbackend.service

import fr.dalkenn.msgbackend.db.User
import fr.dalkenn.msgbackend.db.UserRepository
import org.springframework.stereotype.Service

interface UserService {
    fun createNewUser(userName: String): UserDto

    fun findUserById(userId: Long): User
}

@Service
class UserServiceImpl(
    var repository: UserRepository,
) : UserService {
    override fun createNewUser(userName: String): UserDto {
        checkUserNameIsValid(userName)
        return repository.save(User(userName)).toUserDto()
    }

    fun checkUserNameIsValid(userName: String) {
        if (userName.isBlank()) throw IllegalArgumentException("Username cannot be blank.")
        if (!userName.all { it.isLetterOrDigit() }) throw IllegalArgumentException("Username can contain only letters or numbers.")
        if (repository.existsByUserName(userName)) throw IllegalArgumentException("User name already exists.")
    }

    override fun findUserById(userId: Long): User {
        val user = repository.findUserById(userId) ?: throw IllegalArgumentException("No user found with user id $userId.")
        return user
    }
}

data class UserDto(val userName: String, val id: Long?) {
//    constructor(user: User): this(
//        UserDto(user.userName, user.id)
//    )
}

fun User.toUserDto(): UserDto {
    return UserDto(this.userName, this.id)
}
