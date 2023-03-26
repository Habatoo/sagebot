package com.sage.bot.service

/**
 * Обработка сообщения,
 * не являющегося командой - обычного текста не начинающегося с "/"
 */
class NonCommand {

    /**
     * Обработка сообщений не яляющихся командами
     * @param chatId id чата
     * @param userName имя пользователя
     * @param messageText текст сообщения
     * @return String ответа
     */
    fun nonCommandExecute(chatId: Long, userName: String, messageText: String): String {
        return "*$userName* из чата *$chatId* написал: $messageText"
    }

}