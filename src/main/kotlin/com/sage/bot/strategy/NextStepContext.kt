package com.sage.bot.strategy

import com.sage.bot.enums.StepCode
import com.sage.bot.strategy.stepper.ChooseNextStep
import org.springframework.stereotype.Component

@Component
class NextStepContext(private val chooseNextStep: List<ChooseNextStep>) {

    fun next(chatId: Long, stepCode: StepCode): StepCode? {
        return chooseNextStep.firstOrNull { it.isAvailableForCurrentStep(stepCode) }?.getNextStep(chatId)
    }

}
