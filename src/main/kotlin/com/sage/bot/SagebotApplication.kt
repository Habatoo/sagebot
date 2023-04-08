package com.sage.bot

import com.sage.bot.properties.BotProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(BotProperty::class)
@SpringBootApplication
class SagebotApplication

fun main(args: Array<String>) {
	runApplication<SagebotApplication>(*args)
}
