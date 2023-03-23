package com.sage.bot

import com.sage.bot.config.TelegramConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(TelegramConfig::class)
@SpringBootApplication
class SagebotApplication

fun main(args: Array<String>) {
	runApplication<SagebotApplication>(*args)
}
