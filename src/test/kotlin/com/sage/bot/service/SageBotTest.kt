package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import com.sage.bot.utils.TelegramConfigUtils
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup

class SageBotTest : FunSpec({

    val telegramConfig = mockk<TelegramConfig>()
    TelegramConfigUtils.prepareTelegramConfig(telegramConfig)

    val sageBot = SageBot(telegramConfig)

    context("Send notifications") {
        val getReplyMarkup = sageBot::class.java.getDeclaredMethod(
            "getReplyMarkup", List::class.java
        )
        getReplyMarkup.isAccessible = true

        test("Test parseDate one zero") {
            val replies = listOf("reply")
            val allButtons = listOf(replies)

            val result = getReplyMarkup.invoke(sageBot, allButtons) as ReplyKeyboardMarkup
            result.keyboard[0][0].text shouldBe "reply"
        }
    }
})