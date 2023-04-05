package com.sage.bot.event

import com.sage.bot.enums.StepCode

class TelegramStepMessageEvent(
    val chatId: Long,
    val stepCode: StepCode
)
