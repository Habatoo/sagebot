package com.sage.bot.service.command

import com.sage.bot.util.Utils
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Команда "Спросить"
 */
class AskCommand(
    identifier: String,
    description: String
) : ServiceCommand(
    identifier,
    description
) {
    private lateinit var questionMessage: String

    override fun execute(absSender: AbsSender, user: User, chat: Chat, strings: Array<String>) {
        val userName: String = Utils.getUserName(user)

        sendAnswer(
            absSender,
            chat.id,
            "Пользователь $userName спросил у бота - $questionMessage"
        )
    }

    override fun processMessage(absSender: AbsSender, message: Message, arguments: Array<out String>) {
        super.processMessage(absSender, message, arguments)
        questionMessage = message.text
    }
}