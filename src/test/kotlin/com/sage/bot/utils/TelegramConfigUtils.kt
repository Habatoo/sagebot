package com.sage.bot.utils

import com.sage.bot.properties.BotProperty
import io.mockk.every

object TelegramConfigUtils {

    fun prepareTelegramConfig(botProperty: BotProperty) {
        every { botProperty.botName } returns "botName"
        every { botProperty.botToken } returns "botToken"
        every { botProperty.webhookPath } returns "webhookPath"
    }

}
