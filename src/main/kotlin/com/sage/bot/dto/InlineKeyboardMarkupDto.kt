package com.sage.bot.dto

data class InlineKeyboardMarkupDto(
    /** Сообщение */
    val message: String,
    /** Кнопки под сообщением */
    val inlineButtons: List<MarkupDataDto>
)
