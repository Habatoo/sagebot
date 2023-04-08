package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.ButtonResponseDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ButtonResponseMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(ButtonResponseMessage::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.BUTTON_RESPONSE.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)

        if (user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        return messageWriter.process(
            StepCode.BUTTON_RESPONSE,
            ButtonResponseDto(chatId = chatId, text = user.get().text, accept = user.get().accept)
        )
    }
}
