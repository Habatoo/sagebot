package com.sage.bot.listener

import com.sage.bot.enums.ExecuteStatus
import com.sage.bot.event.TelegramReceivedCallbackEvent
import com.sage.bot.event.TelegramReceivedMessageEvent
import com.sage.bot.event.TelegramStepMessageEvent
import com.sage.bot.service.MessageService
import com.sage.bot.service.UserService
import com.sage.bot.strategy.LogicContext
import com.sage.bot.strategy.NextStepContext
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Lazy
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class ApplicationListener(
    private val logicContext: LogicContext,
    private val nextStepContext: NextStepContext,
    private val userService: UserService,
    private val messageService: MessageService
) {

    private val log = LoggerFactory.getLogger(ApplicationListener::class.java)

    inner class Message {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedMessageEvent) {
            logicContext.execute(chatId = event.chatId, message = event.message)
            val nextStepCode = nextStepContext.next(event.chatId, event.stepCode)
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    inner class StepMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramStepMessageEvent) {
            userService.updateUserStep(event.chatId, event.stepCode)
            messageService.sendMessageToBot(event.chatId, event.stepCode)
        }
    }

    inner class CallbackMessage {
        @EventListener
        fun onApplicationEvent(event: TelegramReceivedCallbackEvent) {
            val nextStepCode = when (logicContext.execute(event.chatId, event.callback)) {
                ExecuteStatus.FINAL -> {
                    nextStepContext.next(event.chatId, event.stepCode)
                }
                ExecuteStatus.NOTHING -> throw IllegalStateException("Не поддерживается")
            }
            if (nextStepCode != null) {
                stepMessageBean().onApplicationEvent(
                    TelegramStepMessageEvent(
                        chatId = event.chatId,
                        stepCode = nextStepCode
                    )
                )
            }
        }
    }

    @Bean
    @Lazy
    fun messageBean(): Message = Message()

    @Bean
    @Lazy
    fun stepMessageBean(): StepMessage = StepMessage()

    @Bean
    @Lazy
    fun callbackMessageBean(): CallbackMessage = CallbackMessage()

}
