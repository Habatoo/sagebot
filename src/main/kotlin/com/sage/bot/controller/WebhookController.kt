package com.sage.bot.controller

import com.sage.bot.service.SageBot
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WebhookController(
    private val sageBot: SageBot,
) {

    @GetMapping("/")
    fun test() = "test"

}
