package fr.dalkenn.msgbackend.db

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Entity
@Table(name = "Users")
data class User(
    var userName: String,
    @Id @GeneratedValue
    var id: Long? = null,
    @OneToMany(mappedBy = "sender", cascade = [CascadeType.ALL])
    var sentMessages: List<Message>? = null,
    @OneToMany(mappedBy = "receiver", cascade = [CascadeType.ALL])
    var receivedMessages: List<Message>? = null,
)

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun save(userName: String): User

    fun findUserById(id: Long): User?

    fun existsByUserName(userName: String): Boolean
}
