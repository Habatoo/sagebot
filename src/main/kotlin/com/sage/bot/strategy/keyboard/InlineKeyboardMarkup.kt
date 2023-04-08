package com.sage.bot.strategy.keyboard

import com.sage.bot.dto.MarkupDataDto
import com.sage.bot.dto.markup.DataModel

interface InlineKeyboardMarkup<T: DataModel> {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun message(chatId: Long, data: T?): String

    fun inlineButtons(chatId: Long, data: T?): List<MarkupDataDto>

    fun getData(chatId: Long): T?
}