package com.sage.bot.strategy.logic

import org.telegram.telegrambots.meta.api.objects.Message

interface MessageChooser: Chooser {
    fun execute(chatId: Long, message: Message)
}
