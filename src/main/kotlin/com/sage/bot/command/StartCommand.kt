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
class StartCommand(
    private val userService: UserService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.START.command, CommandCode.START.desc) {

    companion object {
        private val START_CODE = StepCode.START
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        if (userService.isUserExist(chatId)) {
            userService.updateUserStep(chatId, START_CODE)
        } else userService.createUser(chatId)

        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = START_CODE)
        )
    }

}
