package com.sage.bot.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Контроллер для доступа через веб.
 * Пока использется только для теста.
 */
@RestController
class WebController {

    @GetMapping("/")
    fun test() = "test"

}
