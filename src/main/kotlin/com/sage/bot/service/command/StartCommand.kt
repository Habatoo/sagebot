package com.sage.bot.service.command

import com.sage.bot.util.Utils
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Команда "Старт"
 */
class StartCommand(
    identifier: String,
    description: String
) : ServiceCommand(
    identifier,
    description
) {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, strings: Array<String>) {
        val userName: String = Utils.getUserName(user)

        sendAnswer(
            absSender,
            chat.id,
            "Давайте начнём $userName! Если Вам нужна помощь, нажмите /help"
        )
    }
}