package com.sage.bot.dto

data class AskDto(
    val chatId: Long,
    val question: String?,
    val answer: String?
)