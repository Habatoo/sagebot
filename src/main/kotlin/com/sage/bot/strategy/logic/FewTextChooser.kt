package com.sage.bot.strategy.logic

import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class FewTextChooser(
    private val userService: UserService,
) : MessageChooser {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.USER_INFO.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }

    override fun execute(chatId: Long, message: Message) {
        userService.updateText(chatId, message.text)
    }
}
