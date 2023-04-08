package com.sage.bot.service

import com.sage.bot.api.SageBot
import com.sage.bot.dto.MarkupDataDto
import com.sage.bot.dto.markup.DataModel
import com.sage.bot.enums.StepCode
import com.sage.bot.enums.StepType.SIMPLE_TEXT
import com.sage.bot.enums.StepType.INLINE_KEYBOARD_MARKUP
import com.sage.bot.event.TelegramStepMessageEvent
import com.sage.bot.strategy.MarkupContext
import com.sage.bot.strategy.MessageContext
import com.sage.bot.strategy.NextStepContext
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
class MessageService(
    private val sageBot: SageBot,
    private val messageContext: MessageContext,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val markupContext: MarkupContext<DataModel>,
    private val nextStepContext: NextStepContext
) {

    fun sendMessageToBot(
        chatId: Long,
        stepCode: StepCode
    ) {
        when (stepCode.type) {
            SIMPLE_TEXT -> sageBot.execute(simpleTextMessage(chatId))
            INLINE_KEYBOARD_MARKUP -> sageBot.sendInlineKeyboardMarkup(chatId)
        }

        if (!stepCode.botPause) {
            applicationEventPublisher.publishEvent(
                TelegramStepMessageEvent(
                    chatId = chatId,
                    stepCode = nextStepContext.next(chatId, stepCode)!!
                )
            )
        }
    }


    private fun simpleTextMessage(chatId: Long): SendMessage {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = messageContext.getMessage(chatId)
        sendMessage.enableHtml(true)

        val replyKeyboardRemove = ReplyKeyboardRemove()
        replyKeyboardRemove.removeKeyboard = true
        sendMessage.replyMarkup = replyKeyboardRemove
        return sendMessage
    }

    private fun SageBot.sendInlineKeyboardMarkup(chatId: Long) {
        val inlineKeyboardMarkup: InlineKeyboardMarkup
        val messageText: String

        val inlineKeyboardMarkupDto = markupContext.getInlineKeyboardMarkupDto(chatId)!!
        messageText = inlineKeyboardMarkupDto.message
        inlineKeyboardMarkup = inlineKeyboardMarkupDto.inlineButtons.getInlineKeyboardMarkup()

        this.execute(sendMessageWithMarkup(chatId, messageText, inlineKeyboardMarkup))
    }

    private fun sendMessageWithMarkup(
        chatId: Long, messageText: String, inlineKeyboardMarkup: InlineKeyboardMarkup
    ): BotApiMethod<Message> {
        val sendMessage = SendMessage()
        sendMessage.chatId = chatId.toString()
        sendMessage.text = messageText

        sendMessage.replyMarkup = inlineKeyboardMarkup
        sendMessage.parseMode = ParseMode.HTML
        return sendMessage
    }

    private fun List<MarkupDataDto>.getInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val inlineKeyboardMarkup = InlineKeyboardMarkup()
        val inlineKeyboardButtonsInner: MutableList<InlineKeyboardButton> = mutableListOf()
        val inlineKeyboardButtons: MutableList<MutableList<InlineKeyboardButton>> = mutableListOf()
        this.sortedBy { it.rowPos }.forEach { markupDataDto ->
            val button = InlineKeyboardButton()
                .also { it.text = markupDataDto.text }
                .also { it.callbackData = markupDataDto.text }
            inlineKeyboardButtonsInner.add(button)
        }
        inlineKeyboardButtons.add(inlineKeyboardButtonsInner)
        inlineKeyboardMarkup.keyboard = inlineKeyboardButtons
        return inlineKeyboardMarkup
    }
}
