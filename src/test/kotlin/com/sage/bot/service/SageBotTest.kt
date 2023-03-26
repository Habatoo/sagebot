package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import com.sage.bot.utils.TelegramConfigUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class SageBotTest : FunSpec({

    val telegramConfig = mockk<TelegramConfig>()
    TelegramConfigUtils.prepareTelegramConfig(telegramConfig)

    val sageBot = SageBot(telegramConfig)

    context("TelegramConfig") {

        test("Test token") {
            sageBot.botToken shouldBe "botToken"
        }

        test("Test botName") {
            sageBot.botUsername shouldBe "botName"
        }
    }
})