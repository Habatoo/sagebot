package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component

@Component
class HelpMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.HELP.toString()
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.HELP)
    }
}
