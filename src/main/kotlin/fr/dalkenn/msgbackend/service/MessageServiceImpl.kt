package fr.dalkenn.msgbackend.service

import fr.dalkenn.msgbackend.controller.SearchMessageType
import fr.dalkenn.msgbackend.db.Message
import fr.dalkenn.msgbackend.db.MessageRepository
import fr.dalkenn.msgbackend.exceptions.CannotSendMessageToSelfException
import org.springframework.stereotype.Service

interface MessageService {
    fun sendMessage(message: MessageDto)

    fun checkSenderAndReceiverAreSameUser(message: MessageDto)

    fun findMessagesForUser(
        userId: Long,
        search: SearchMessageType,
    ): List<MessageDto>
}

@Service
class MessageServiceImpl(
    val userService: UserServiceImpl,
    val messageRepository: MessageRepository,
) : MessageService {
    override fun sendMessage(message: MessageDto) {
        checkSenderAndReceiverAreSameUser(message)

        val sender = userService.findUserById(message.senderId)
        val receiver = userService.findUserById(message.receiverId)
        messageRepository.save(Message(sender, receiver, message.message))
    }

    override fun checkSenderAndReceiverAreSameUser(message: MessageDto) {
        if (message.senderId == message.receiverId) {
            throw CannotSendMessageToSelfException("You cannot send a message to yourself.")
        }
    }

    override fun findMessagesForUser(
        userId: Long,
        search: SearchMessageType,
    ): List<MessageDto> {
        val user = userService.findUserById(userId)

        val messages =
            when (search) {
                SearchMessageType.SENT -> user.sentMessages ?: emptyList()
                SearchMessageType.RECEIVED -> user.receivedMessages ?: emptyList()
                else -> emptyList()
            }

        return messages.map { MessageDto(it.sender.id!!, it.receiver.id!!, it.msg) }
    }
}

data class MessageDto(val senderId: Long, val receiverId: Long, val message: String)
