package com.sage.bot.utils

import com.sage.bot.config.TelegramConfig
import io.mockk.every

object TelegramConfigUtils {

    fun prepareTelegramConfig(telegramConfig: TelegramConfig) {
        every { telegramConfig.botName } returns "botName"
        every { telegramConfig.botToken } returns "botToken"
        every { telegramConfig.webhookPath } returns "webhookPath"
    }

}
