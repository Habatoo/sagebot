package com.sage.bot.dto

data class CleanMyDataDto(
    val chatId: Long,
    val userName: String?,
    val text: String?,
)