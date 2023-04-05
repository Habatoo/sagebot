package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.ButtonResponseDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component

@Component
class ButtonResponseMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.BUTTON_RESPONSE.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)
        return messageWriter.process(
            StepCode.BUTTON_RESPONSE,
            ButtonResponseDto(chatId = chatId, text = user.get().text, accept = user.get().accept)
        )
    }
}
