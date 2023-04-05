package com.sage.bot.strategy.message

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.UserInfoDto
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.springframework.stereotype.Component

@Component
class UserInfoMessage(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : Message {

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.USER_INFO.toString()
    }

    override fun getMessage(chatId: Long): String {
        return messageWriter.process(StepCode.USER_INFO, UserInfoDto(chatId))
    }
}
