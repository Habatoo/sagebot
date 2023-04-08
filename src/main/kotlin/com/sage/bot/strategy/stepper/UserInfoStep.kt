package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode
import org.springframework.stereotype.Component

@Component
class UserInfoStep : ChooseNextStep {

    override fun isAvailableForCurrentStep(stepCode: StepCode): Boolean {
        return stepCode == StepCode.USER_INFO
    }

    override fun getNextStep(chatId: Long): StepCode {
        return StepCode.ASK
    }

}
