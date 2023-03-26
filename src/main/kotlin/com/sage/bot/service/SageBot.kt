package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import com.sage.bot.service.command.AskCommand
import com.sage.bot.service.command.HelpCommand
import com.sage.bot.service.command.StartCommand
import com.sage.bot.util.Utils
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
class SageBot(
    private val telegramConfig: TelegramConfig,
) : TelegramLongPollingCommandBot() {

    var nonCommand: NonCommand = NonCommand()

    init {
        register(StartCommand("start", "Старт"))
        register(AskCommand("ask", "Спросить"))
        register(HelpCommand("help", "Помощь"))
    }

    override fun getBotUsername(): String {
        return telegramConfig.botName
    }

    @Deprecated("getBotToken - deprecated")
    override fun getBotToken(): String {
        return telegramConfig.botToken
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    override fun processNonCommandUpdate(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val userName = Utils.getUserName(message)
            val responseText = if (message.hasText()) {
                nonCommand.nonCommandExecute(chatId, userName, message.text)
            } else {
                "Только текст"
            }
            sendNotification(chatId, responseText)
        }
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param responseText текст ответа
     */
    private fun sendNotification(chatId: Long, responseText: String) {
        val responseMessage = SendMessage(chatId.toString(), responseText)
        responseMessage.enableMarkdown(true)
        try {
            execute(responseMessage)
        } catch (e: TelegramApiException) {
            e.printStackTrace()
        }
    }

}
