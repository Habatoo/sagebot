package com.sage.bot.dto

data class UserInfoDto(
    val chatId: Long,
    val userName: String?,
    val firstName: String?,
    val lastName: String?,
    val registerAt: String?,
)
