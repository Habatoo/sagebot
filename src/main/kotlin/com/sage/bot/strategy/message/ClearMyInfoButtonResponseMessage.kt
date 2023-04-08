package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.ButtonResponseDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import com.sage.bot.util.Utils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ClearMyInfoButtonResponseMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(ClearMyInfoButtonResponseMessage::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.CLEAN_MY_INFO_BUTTON_RESPONSE.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.CLEAN_MY_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        val user = userService.getUser(chatId)

        if (!userService.isUserExist(chatId) || user.isEmpty) {
            val errorMessage = "Not found user with id $chatId"
            log.error(errorMessage)
            throw Exception(errorMessage)
        }

        val userName = Utils.getUserName(user.get())
        val accept = user.get().accept
        val text = checkYesOrNo(accept!!,"Пользователь $userName", chatId)

        return messageWriter.process(
            StepCode.CLEAN_MY_INFO_BUTTON_RESPONSE,
            ButtonResponseDto(chatId = chatId, text = text, accept = user.get().accept)
        )
    }

    private fun checkYesOrNo(callbackQueryAnswer: String, text: String, chatId: Long): String {
        return if ("YES" == callbackQueryAnswer) {
            userService.delete(chatId)
            "$text из чата $chatId удалил свои пользовательские данные"
        } else {
            "$text отказался удалять свои пользовательские данные"
        }
    }
}
