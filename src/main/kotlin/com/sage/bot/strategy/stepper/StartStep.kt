package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode
import org.springframework.stereotype.Component

@Component
class StartStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.START
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.USER_INFO
    }

}
