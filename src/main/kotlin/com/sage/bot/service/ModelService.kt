package com.sage.bot.service

import com.sage.bot.client.chat.ChatClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ModelService(
    private val userService: UserService,
    private val chatClient: ChatClient,
) {

    private val log = LoggerFactory.getLogger(ModelService::class.java)

    fun takeAnswer(chatId: Long) {
        val user = userService.getUser(chatId)

        if (user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        val question = user.get().text
        val answerDto = chatClient.ask(chatId, question!!)

        if (question != null && answerDto != null) {
            log.info("Для запроса $question получен ответ ${answerDto.text!!}")
            userService.updateText(chatId, answerDto.text)
        } else {
            log.info("Для запроса $question получен пустой ответ")
            userService.updateText(chatId, "")
        }
    }

}
