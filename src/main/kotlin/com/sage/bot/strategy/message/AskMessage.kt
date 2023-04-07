package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.AskDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component

@Component
class AskMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.ASK.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)
        return messageWriter.process(StepCode.ASK, AskDto(chatId, user.get().text))
    }
}
