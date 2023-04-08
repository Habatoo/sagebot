package com.sage.bot.strategy

import com.sage.bot.enums.ExecuteStatus
import com.sage.bot.strategy.logic.CallbackChooser
import com.sage.bot.strategy.logic.Chooser
import com.sage.bot.strategy.logic.MessageChooser
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message

@Component
class LogicContext(private val chooser: List<Chooser>) {

    fun execute(chatId: Long, message: Message) {
        chooser.filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .forEach {
                (it as MessageChooser).execute(chatId = chatId, message = message)
            }
    }

    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus {
        return chooser
            .filter { it.isAvailableForCurrentStep(chatId) }
            .filter { it.isPermitted(chatId) }
            .map { (it as CallbackChooser).execute(chatId = chatId, callbackQuery = callbackQuery) }
            .first()
    }
}