package com.sage.bot.strategy.logic

import com.sage.bot.enums.ExecuteStatus
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

@Component
class AcceptChooser(
    private val userService: UserService,
) : CallbackChooser {

    private val log = LoggerFactory.getLogger(AcceptChooser::class.java)

    override fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        userService.updateAccept(chatId, callbackQuery.data)
        return ExecuteStatus.FINAL
    }

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.BUTTON_REQUEST.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.CLEAN_MY_INFO_BUTTON_REQUEST.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.REGISTER_BUTTON_REQUEST.toString()
    }

    override fun isPermitted(chatId: Long): Boolean {
        return true
    }
}
