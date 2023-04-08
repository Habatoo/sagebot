package com.sage.bot.event

import com.sage.bot.enums.StepCode
import org.telegram.telegrambots.meta.api.objects.CallbackQuery

class TelegramReceivedCallbackEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val callback: CallbackQuery
)
