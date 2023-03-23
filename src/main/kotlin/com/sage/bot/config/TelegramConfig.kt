package com.sage.bot.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("telegram")
class TelegramConfig {
    lateinit var webhookPath: String
    lateinit var botName: String
    lateinit var botToken: String
}
