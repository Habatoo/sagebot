package com.sage.bot.strategy.logic

import com.sage.bot.enums.ExecuteStatus
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

@Component
class AcceptChooser(
    private val userService: UserService,
) : CallbackChooser {

    override fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        userService.updateAccept(chatId, callbackQuery.data)
        return ExecuteStatus.FINAL
    }

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.BUTTON_REQUEST.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}
