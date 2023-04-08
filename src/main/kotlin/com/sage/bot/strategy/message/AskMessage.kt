package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.AskDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AskMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(AskMessage::class.java)
    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.ASK.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)

        if (user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        return messageWriter.process(StepCode.ASK, AskDto(chatId, user.get().text))
    }
}
