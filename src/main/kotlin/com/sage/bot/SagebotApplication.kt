package com.sage.bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SagebotApplication

fun main(args: Array<String>) {
	runApplication<SagebotApplication>(*args)
}
