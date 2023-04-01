package com.sage.bot.service

import com.sage.bot.config.TelegramConfig
import com.vdurmont.emoji.EmojiParser
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException

const val HELP_TEXT = "Help text";
const val YES_BUTTON = "YES_BUTTON";
const val NO_BUTTON = "NO_BUTTON";
const val ERROR_TEXT = "Error occurred: ";
@Component
data class SageBot(
    private val telegramConfig: TelegramConfig,
) : TelegramLongPollingBot(), SageBotInterface {

    private val log = LoggerFactory.getLogger(SageBot::class.java)

    init {
        val listofCommands: MutableList<BotCommand> = ArrayList<BotCommand>()
        listofCommands.add(BotCommand("/start", "get a welcome message"))
        listofCommands.add(BotCommand("/ask", "bot ask"))
        listofCommands.add(BotCommand("/help", "info how to use this bot"))
        try {
            this.execute(SetMyCommands(listofCommands, BotCommandScopeDefault(), null))
        } catch (e: TelegramApiException) {
            log.error("Error setting bot's command list: " + e.message)
        }
    }

    override fun getBotUsername() = telegramConfig.botName

    override fun getBotToken() = telegramConfig.botToken

    /**
     * Ответ на запрос, не являющийся командой
     */
    override fun onUpdateReceived(update: Update) {
        if (update.hasMessage() && update.message.hasText()) {
            val messageText = update.message.text
            val chatId = update.message.chatId
            if (messageText.contains("/send")) {
                val textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")))
                prepareAndSendMessage(chatId, textToSend)
            } else {
                when (messageText) {
                    "/start" -> {
                        startCommandReceived(chatId, update.message.chat.firstName)
                    }
                    "/ask" -> prepareAndSendMessage(chatId, "Ask $messageText")
                    "/help" -> prepareAndSendMessage(chatId, HELP_TEXT)
                    else -> prepareAndSendMessage(chatId, "Sorry, command was not recognized")
                }
            }
        } else if (update.hasCallbackQuery()) {
            val callbackData = update.callbackQuery.data
            val messageId = update.callbackQuery.message.messageId.toLong()
            val chatId = update.callbackQuery.message.chatId
            if (callbackData == YES_BUTTON) {
                val text = "You pressed YES button"
                executeEditMessageText(text, chatId, messageId)
            } else if (callbackData == NO_BUTTON) {
                val text = "You pressed NO button"
                executeEditMessageText(text, chatId, messageId)
            }
        }
    }

    private fun startCommandReceived(chatId: Long, name: String) {
        val answer: String = EmojiParser.parseToUnicode("Hi, $name, nice to meet you! :blush:")
        log.info("Replied to user $name")
        sendMessage(chatId, answer)
    }

    private fun sendMessage(chatId: Long, textToSend: String) {
        val message = SendMessage()
        message.chatId = chatId.toString()
        message.text = textToSend
        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboardRows: MutableList<KeyboardRow> = ArrayList()
        var row = KeyboardRow()
        row.add("weather")
        row.add("get random joke")
        keyboardRows.add(row)
        row = KeyboardRow()
        row.add("register")
        row.add("check my data")
        row.add("delete my data")
        keyboardRows.add(row)
        keyboardMarkup.keyboard = keyboardRows
        message.replyMarkup = keyboardMarkup
        executeMessage(message)
    }

    private fun executeEditMessageText(text: String, chatId: Long, messageId: Long) {
        val message = EditMessageText()
        message.chatId = chatId.toString()
        message.text = text
        message.messageId = messageId.toInt()
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            log.error(ERROR_TEXT + e.message)
        }
    }

    private fun executeMessage(message: SendMessage) {
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            log.error(ERROR_TEXT + e.message)
        }
    }

    private fun prepareAndSendMessage(chatId: Long, textToSend: String) {
        val message = SendMessage()
        message.chatId = chatId.toString()
        message.text = textToSend
        executeMessage(message)
    }

}

interface SageBotInterface {
    fun onUpdateReceived(update: Update)
}
