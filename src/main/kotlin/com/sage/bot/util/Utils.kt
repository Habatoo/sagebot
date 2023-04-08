package com.sage.bot.util

import com.sage.bot.repository.entity.UserEntity
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.User
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object Utils {

    private val zone: ZoneId = ZoneId.of("America/Edmonton") // TODO zone in user setting
    private val PATTERN_FORMAT = "dd.MM.yyyy HH:mm:ss a z"
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(zone)

    /**
     * Форматирование даты регистрации пользователя
     * @param instant время регистрации
     * @return String отформатированного время регистрации
     */
    fun obtainDateTime(instant: Instant) = formatter.format(instant)

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

    fun getUserName(user: UserEntity) = user.userName ?: "${user.lastName} ${user.firstName}"

}