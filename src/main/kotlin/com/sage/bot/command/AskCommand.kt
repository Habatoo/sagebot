package com.sage.bot.command

import com.sage.bot.enums.CommandCode
import com.sage.bot.enums.StepCode
import com.sage.bot.event.TelegramStepMessageEvent
import com.sage.bot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.bots.AbsSender

@Component
class AskCommand(
    private val userService: UserService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.ASK.command, CommandCode.ASK.desc) {

    private val log = LoggerFactory.getLogger(AskCommand::class.java)

    companion object {
        private val ASK = StepCode.ASK
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<String>) {
        val chatId = chat.id
        val question = arguments.toList().joinToString(
            prefix = "",
            separator = " ",
            postfix = "",
            truncated = "...",
            transform = { it }
        )

        userService.updateUserStep(chatId, ASK)
        userService.updateText(chatId, question)
        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = ASK)
        )
    }

}
