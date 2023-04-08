package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.UserInfoDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import com.sage.bot.util.Utils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserInfoMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(UserInfoMessage::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.USER_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)

        if (user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        return messageWriter.process(
            StepCode.USER_INFO,
            UserInfoDto(
                chatId = chatId,
                userName = user.get().userName,
                firstName = user.get().firstName,
                lastName = user.get().lastName,
                registerAt = Utils.obtainDateTime(user.get().registerAt),
            )
        )
    }
}
