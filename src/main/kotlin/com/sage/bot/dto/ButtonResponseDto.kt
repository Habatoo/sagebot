package com.sage.bot.dto

data class ButtonResponseDto(
    val chatId: Long,
    val text: String?,
    val accept: String?
)