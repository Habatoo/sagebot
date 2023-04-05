package com.sage.bot.api

import com.sage.bot.properties.BotProperty
import com.sage.bot.service.ReceiverService
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import javax.annotation.PostConstruct

@Component
class SageBot(
    private val botProperty: BotProperty,
    private val botCommands: List<IBotCommand>,
    private val receiverService: ReceiverService
) : TelegramLongPollingCommandBot() {

    @PostConstruct
    fun initCommands() {
        botCommands.forEach {
            register(it)
        }

        registerDefaultAction { absSender, message ->

            val commandUnknownMessage = SendMessage()
            commandUnknownMessage.chatId = message.chatId.toString()
            commandUnknownMessage.text = "Command '" + message.text.toString() + "' unknown"

            absSender.execute(commandUnknownMessage)
        }
    }

    override fun getBotToken() = botProperty.botToken

    override fun getBotUsername() = botProperty.botName

    override fun processNonCommandUpdate(update: Update) {
        receiverService.execute(update)
    }
}
