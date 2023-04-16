package com.sage.bot.strategy.keyboard

import com.sage.bot.component.MessageWriter
import com.sage.bot.dto.MarkupDataDto
import com.sage.bot.dto.markup.ButtonRequestDto
import com.sage.bot.dto.markup.DataModel
import com.sage.bot.enums.StepCode
import com.sage.bot.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ButtonRequestMarkup<T : DataModel>(
    private val userService: UserService,
    private val messageWriter: MessageWriter,
) : InlineKeyboardMarkup<ButtonRequestDto> {

    private val log = LoggerFactory.getLogger(ButtonRequestMarkup::class.java)

    override fun isAvailableForCurrentStep(chatId: Long): Boolean {
        return userService.getUser(chatId).get().stepCode == StepCode.BUTTON_REQUEST.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.CLEAN_MY_INFO_BUTTON_REQUEST.toString()
                || userService.getUser(chatId).get().stepCode == StepCode.REGISTER_BUTTON_REQUEST.toString()
    }

    override fun message(chatId: Long, data: ButtonRequestDto?): String {
        if (data == null) {
            throw IllegalStateException("Not Yet Supported")
        }

        val stepCode = userService.getUser(chatId).get().stepCode

        if (stepCode == StepCode.CLEAN_MY_INFO_BUTTON_REQUEST.name) {
            return messageWriter.process(StepCode.CLEAN_MY_INFO_BUTTON_REQUEST)
        }

        if (stepCode == StepCode.REGISTER_BUTTON_REQUEST.name) {
            return messageWriter.process(StepCode.REGISTER_BUTTON_REQUEST)
        }

        return messageWriter.process(StepCode.BUTTON_REQUEST)
    }

    override fun inlineButtons(chatId: Long, data: ButtonRequestDto?): List<MarkupDataDto> {
        val accept = data!!.accept
        return listOf(MarkupDataDto(0, accept.first()), MarkupDataDto(1, accept.last()))
    }

    override fun getData(chatId: Long): ButtonRequestDto {
        return ButtonRequestDto(listOf("YES", "NO"))
    }

}