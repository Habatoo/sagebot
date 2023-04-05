package com.sage.bot.command

import com.sage.bot.enums.CommandCode
import com.sage.bot.enums.StepCode
import com.sage.bot.event.TelegramStepMessageEvent
import com.sage.bot.service.UserService
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class UserInfoCommand(
    private val userService: UserService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.USER_INFO.command, CommandCode.USER_INFO.desc) {

    companion object {
        private val USER_INFO = StepCode.USER_INFO
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        userService.updateUserStep(chatId, USER_INFO)
        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = USER_INFO)
        )
    }

}
