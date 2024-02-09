package fr.dalkenn.msgbackend.controller

import fr.dalkenn.msgbackend.service.UserDto
import fr.dalkenn.msgbackend.service.UserServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1")
class UserController(private val userService: UserServiceImpl) {
    @PostMapping("/users", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createUser(
        @RequestBody user: CreateUserDto,
    ): ResponseEntity<Any> {
        try {
            val createdUser = userService.createNewUser(user.userName)
            return ResponseEntity<Any>(createdUser.toUserModel(), HttpStatus.CREATED)
        } catch (ex: IllegalArgumentException) {
            return ResponseEntity<Any>(ex.message, HttpStatus.CONFLICT)
        }
    }
}

data class CreateUserDto(val userName: String)

data class UserModel(val user: UserDto)

private fun UserDto.toUserModel(): UserModel {
    return UserModel(this)
}
