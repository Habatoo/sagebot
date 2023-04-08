package com.sage.bot.strategy

import com.sage.bot.dto.InlineKeyboardMarkupDto
import com.sage.bot.dto.markup.DataModel
import com.sage.bot.strategy.keyboard.InlineKeyboardMarkup
import org.springframework.stereotype.Component

@Component
class MarkupContext<T : DataModel>(private val inlineKeyboardMarkup: List<InlineKeyboardMarkup<T>>) {

    fun getInlineKeyboardMarkupDto(
        chatId: Long
    ): InlineKeyboardMarkupDto? {
        return inlineKeyboardMarkup
            .firstOrNull { it.isAvailableForCurrentStep(chatId) }
            ?.let {
                val data = it.getData(chatId)
                InlineKeyboardMarkupDto(
                    it.message(chatId, data),
                    it.inlineButtons(chatId, data)
                )
            }
    }
}
