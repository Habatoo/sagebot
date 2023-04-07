package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.StartDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component

@Component
class StartMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.START.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)
        return messageWriter.process(StepCode.START, StartDto(user.get().id)) // todo
    }
}
