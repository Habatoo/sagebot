package com.sage.bot.dto

import java.time.Instant
data class UserInfoDto(
    val chatId: Long,
    val userName: String?,
    val firstName: String?,
    val lastName: String?,
    val registerAt: Instant?,
)
