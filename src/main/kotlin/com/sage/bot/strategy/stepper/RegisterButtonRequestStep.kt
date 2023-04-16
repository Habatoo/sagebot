package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode
import org.springframework.stereotype.Component

@Component
class RegisterButtonRequestStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.REGISTER_BUTTON_REQUEST
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.REGISTER_BUTTON_RESPONSE
    }

}
