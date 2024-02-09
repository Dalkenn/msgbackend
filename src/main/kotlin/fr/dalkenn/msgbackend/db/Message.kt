package fr.dalkenn.msgbackend.db

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository

@Table(name = "Messages", indexes = [Index(columnList = "sender, receiver")])
@Entity
data class Message(
    @ManyToOne
    @JoinColumn(name = "sender")
    var sender: User,
    @ManyToOne
    @JoinColumn(name = "receiver")
    var receiver: User,
    var msg: String,
    @Id @GeneratedValue
    var id: Long? = null,
)

interface MessageRepository : CrudRepository<Message, Long> {
    fun save(message: Message)
}
