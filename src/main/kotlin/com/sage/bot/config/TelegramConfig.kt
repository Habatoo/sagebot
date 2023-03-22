package com.sage.bot.config

import org.springframework.context.annotation.Configuration

@Configuration("telegram")
class TelegramConfig {
    lateinit var webhookPath: String
    lateinit var botName: String
    lateinit var botToken: String
}
