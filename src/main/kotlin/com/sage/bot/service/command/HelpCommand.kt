package com.sage.bot.service.command

import com.sage.bot.util.Utils
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

/**
 * Команда "Помощь"
 */
class HelpCommand(
    identifier: String,
    description: String,
) : ServiceCommand(
    identifier,
    description,
) {
    override fun execute(absSender: AbsSender, user: User, chat: Chat, strings: Array<String>) {
        val userName: String = Utils.getUserName(user)

        sendAnswer(
            absSender, chat.id,
            """
                Я бот, советник, отвечаю на вопросы.  
                
                ❗*Список команд*
                /start - начать
                /ask - задать вопрос боту (вопрос нужно писать после команды /ask в одном сообщении, 
                если команда /ask отправлена пустой, то вопрос можно написать в следущем сообщении)
                /help - помощь
                
                Задай вопрос и получишь ответ 
                👉 Например, $userName: /ask Как поднять денег?
    
                Желаю удачи🙂
                """.trimIndent()
        )
    }
}