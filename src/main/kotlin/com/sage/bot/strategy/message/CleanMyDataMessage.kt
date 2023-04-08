package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.StartDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import com.sage.bot.util.Utils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CleanMyDataMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(CleanMyDataMessage::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.CLEAN_MY_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)

        if (user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        return messageWriter.process(
            StepCode.CLEAN_MY_INFO,
            StartDto(
                chatId = user.get().id,
                userName = Utils.getUserName(user.get()),
            )
        )
    }
}