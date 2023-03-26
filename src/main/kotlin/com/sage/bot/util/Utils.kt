package com.sage.bot.util

import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User

object Utils {
    /**
     * Формирование имени пользователя
     * @param message сообщение
     * @return String отформатированного имени пользователя
     */
    fun getUserName(message: Message) = getUserName(message.from)

    /**
     * Формирование имени пользователя.
     * Если заполнен никнейм, используем его. Если нет - используем фамилию и имя.
     * @param user пользователь
     * @return String имя пользователя
     */
    fun getUserName(user: User) = user.userName ?: "${user.lastName} ${user.firstName}"

}