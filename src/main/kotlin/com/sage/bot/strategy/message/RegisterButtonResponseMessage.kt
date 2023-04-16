package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.ButtonResponseDto
import com.sage.bot.dto.StartDto
import com.sage.bot.enums.StepCode
import com.sage.bot.repository.entity.UserEntity
import com.sage.bot.service.UserService
import com.sage.bot.util.Utils
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class RegisterButtonResponseMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    private val log = LoggerFactory.getLogger(ClearMyInfoButtonResponseMessage::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.REGISTER_BUTTON_RESPONSE.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.START.toString()
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
        return checkRegisterYesOrNo(accept!!,"Пользователь $userName", chatId, user)

    }

    private fun checkRegisterYesOrNo(
        callbackQueryAnswer: String,
        text: String,
        chatId: Long,
        user: Optional<UserEntity>,
    ): String {
        return if ("YES" == callbackQueryAnswer) {
            messageWriter.process(
                StepCode.START,
                StartDto(
                    chatId = user.get().id,
                    userName = Utils.getUserNameWithSmile(user.get()),
                )
            )

        } else {
            userService.delete(chatId)
            messageWriter.process(
                StepCode.REGISTER_BUTTON_RESPONSE,
                ButtonResponseDto(
                    chatId = chatId,
                    text = "$text отказался регистрироваться",
                    accept = user.get().accept
                )
            )
        }
    }

}