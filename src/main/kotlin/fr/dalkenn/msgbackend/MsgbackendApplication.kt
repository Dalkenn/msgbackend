package fr.dalkenn.msgbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class MsgbackendApplication

fun main(args: Array<String>) {
    runApplication<MsgbackendApplication>(*args)
}
