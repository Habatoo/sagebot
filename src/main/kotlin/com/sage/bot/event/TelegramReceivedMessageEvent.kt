package com.sage.bot.event

import com.sage.bot.enums.StepCode
import org.telegram.telegrambots.meta.api.objects.Message

class TelegramReceivedMessageEvent(
    val chatId: Long,
    val stepCode: StepCode,
    val message: Message
)