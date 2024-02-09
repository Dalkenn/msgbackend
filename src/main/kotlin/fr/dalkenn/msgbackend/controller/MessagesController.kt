package fr.dalkenn.msgbackend.controller

import fr.dalkenn.msgbackend.service.MessageDto
import fr.dalkenn.msgbackend.service.MessageService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1")
class MessagesController(
    private val messageService: MessageService,
) {
    @PostMapping(
        "/messages",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun sendMessage(
        @RequestBody message: SendMessageDto,
    ): ResponseEntity<Any> {
        try {
            messageService.sendMessage(message.toMessageDto())
            return ResponseEntity<Any>(HttpStatus.CREATED)
        } catch (ex: RuntimeException) {
            return ResponseEntity<Any>(ex.message, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("/messages/{userId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listMessagesForUser(
        @PathVariable userId: Long,
        @RequestParam(value = "search") search: SearchMessageType,
    ): ResponseEntity<List<MessageDto>> {
        return ResponseEntity<List<MessageDto>>(messageService.findMessagesForUser(userId, search), HttpStatus.OK)
    }
}

data class SendMessageDto(val senderId: Long, val receiverId: Long, val message: String)

private fun SendMessageDto.toMessageDto(): MessageDto {
    return MessageDto(this.senderId, this.receiverId, this.message)
}

enum class SearchMessageType(val type: String) {
    SENT("sent"),
    RECEIVED("received"),
}
