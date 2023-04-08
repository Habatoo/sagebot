package com.sage.bot.controller

import com.sage.bot.api.SageBot
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Контроллер для доступа через веб.
 * Пока использется только для теста.
 */
@RestController
class WebController(
    private val sageBot: SageBot,
) {

    private val log = LoggerFactory.getLogger(WebController::class.java)

    @GetMapping("/")
    fun test() = "test"

}
