package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode
import org.springframework.stereotype.Component

@Component
class ButtonRequestStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.BUTTON_REQUEST
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_RESPONSE
    }

}
