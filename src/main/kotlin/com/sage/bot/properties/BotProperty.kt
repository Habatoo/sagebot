package com.sage.bot.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("telegram")
class BotProperty {
    lateinit var webhookPath: String
    lateinit var botName: String
    lateinit var botToken: String

    lateinit var chatPort: Integer
    lateinit var chatHost: String
}
