package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import com.sage.bot.util.Utils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

@Component
data class SageBot(
    private val telegramConfig: TelegramConfig,
) : TelegramLongPollingBot(), SageBotInterface {

    private val log = LoggerFactory.getLogger(SageBot::class.java)

    override fun getBotUsername() = telegramConfig.botName

    override fun getBotToken() = telegramConfig.botToken

    /**
     * Ответ на запрос, не являющийся командой
     */
    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage()) {
            val message = update.message
            val chatId = message.chatId
            val userName = Utils.getUserName(message)
            val responseText = if (message.hasText()) {
                when (message.text) {
                    "/start" -> message.text
                    else -> {
                        log.error("Не правильная команда")
                        "Не правильная команда"
                    }
                }

            } else {
                log.error("Поддерживается только текст")
                "Поддерживается только текст"
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
            setButtons(responseMessage)
            execute(responseMessage)
        } catch (e: TelegramApiException) {
            log.error("sendNotification error ${e.message}")
            e.printStackTrace()
        }
    }

    fun setButtons(sendMessage: SendMessage) {
        // Создаем клавиуатуру
        val replyKeyboardMarkup = ReplyKeyboardMarkup()
        sendMessage.replyMarkup = replyKeyboardMarkup
        replyKeyboardMarkup.selective = true
        replyKeyboardMarkup.resizeKeyboard = true
        replyKeyboardMarkup.oneTimeKeyboard = false

        // Создаем список строк клавиатуры
        val keyboard = ArrayList<KeyboardRow>()

        // Первая строчка клавиатуры
        val keyboardFirstRow = KeyboardRow()
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(KeyboardButton("Спросить"))

        // Вторая строчка клавиатуры
        val keyboardSecondRow = KeyboardRow()
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(KeyboardButton("Помощь"))

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow)
        keyboard.add(keyboardSecondRow)
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.keyboard = keyboard
    }

    @Synchronized
    private fun setInline() {
        val buttons = ArrayList<ArrayList<InlineKeyboardButton>>()

        val buttons1 = ArrayList<InlineKeyboardButton>()
        val inlineKeyboardButton = InlineKeyboardButton()
        inlineKeyboardButton.text = "Кнопка"
        inlineKeyboardButton.callbackData = "17"
        buttons1.add(inlineKeyboardButton)

        buttons.add(buttons1)

        val markupKeyboard = InlineKeyboardMarkup()
        markupKeyboard.keyboard = buttons as List<MutableList<InlineKeyboardButton>>
    }

}

interface SageBotInterface {
    fun onUpdateReceived(update: Update)
}
