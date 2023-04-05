package com.sage.bot.service

import com.sage.bot.enums.StepCode
import com.sage.bot.event.TelegramReceivedCallbackEvent
import com.sage.bot.event.TelegramReceivedMessageEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class ReceiverService(
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val userService: UserService,
) {

    private val log = LoggerFactory.getLogger(ReceiverService::class.java)

    fun execute(update: Update) {
        if (update.hasCallbackQuery()) {
            callbackExecute(update.callbackQuery)
        } else if (update.hasMessage()) {
            messageExecute(update.message)
        } else {
            log.error("Not yet supported")
            throw IllegalStateException("Not yet supported")
        }
    }

    private fun messageExecute(message: Message) {
        val chatId = message.chatId
        val stepCode = userService.getUser(chatId).get().stepCode
        applicationEventPublisher.publishEvent(
            TelegramReceivedMessageEvent(
                chatId = chatId,
                stepCode = StepCode.valueOf(stepCode),
                message = message
            )
        )
    }

    private fun callbackExecute(callback: CallbackQuery) {
        val chatId = callback.from.id
        val stepCode = userService.getUser(chatId).get().stepCode
        applicationEventPublisher.publishEvent(
            TelegramReceivedCallbackEvent(
                chatId = chatId,
                stepCode = StepCode.valueOf(stepCode),
                callback = callback
            )
        )
    }
}
