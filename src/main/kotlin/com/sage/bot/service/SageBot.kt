package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class SageBot(
    telegramConfig: TelegramConfig,
) : TelegramLongPollingBot() {

    private val webhookPath = telegramConfig.webhookPath
    private val botName = telegramConfig.botName
    private val botToken = telegramConfig.botToken

    override fun getBotUsername(): String {
        return botName
    }

    override fun getBotToken(): String {
        return botToken
    }

    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val responseText = if (message.hasText()) {
                val messageText = message.text
                when {
                    messageText == "/start" -> "Добро пожаловать!"
                    messageText.startsWith("Кнопка ") -> "Вы нажали кнопку"
                    else -> "Вы написали: *$messageText*"
                }
            } else {
                "Я понимаю только текст"
            }
            sendNotification(chatId, responseText)
        }
    }

    private fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdownV2(true)
        execute(responseMessage)
    }
}
