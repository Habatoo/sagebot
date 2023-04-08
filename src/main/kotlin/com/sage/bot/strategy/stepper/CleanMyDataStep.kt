package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode
import org.springframework.stereotype.Component

@Component
class CleanMyDataStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.CLEAN_MY_INFO
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.BUTTON_REQUEST
    }

}