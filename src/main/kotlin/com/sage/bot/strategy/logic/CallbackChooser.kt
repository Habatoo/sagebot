package com.sage.bot.strategy.logic

import com.sage.bot.enums.ExecuteStatus
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

interface CallbackChooser : Chooser {
    fun execute(chatId: Long, callbackQuery: CallbackQuery): ExecuteStatus
}
