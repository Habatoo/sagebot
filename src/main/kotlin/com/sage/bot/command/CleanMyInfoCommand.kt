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
class CleanMyInfoCommand(
    private val userService: UserService,
    private val applicationEventPublisher: ApplicationEventPublisher
) : BotCommand(CommandCode.CLEAN_MY_INFO.command, CommandCode.CLEAN_MY_INFO.desc) {

    private val log = LoggerFactory.getLogger(CleanMyInfoCommand::class.java)

    companion object {
        private val CLEAN_MY_INFO = StepCode.CLEAN_MY_INFO
    }

    override fun execute(absSender: AbsSender, user: User, chat: Chat, arguments: Array<out String>) {
        val chatId = chat.id

        if (userService.isUserExist(chatId)) {
            userService.updateUserStep(chatId, CLEAN_MY_INFO)
            userService.delete(chatId)
        } else {
            log.info("Not found user for id $chatId")
        }

        userService.updateUserStep(chatId, CLEAN_MY_INFO)
        applicationEventPublisher.publishEvent(
            TelegramStepMessageEvent(chatId = chatId, stepCode = CLEAN_MY_INFO)
        )
    }

}