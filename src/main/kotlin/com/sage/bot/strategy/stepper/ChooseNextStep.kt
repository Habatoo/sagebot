package com.sage.bot.strategy.stepper

import com.sage.bot.enums.StepCode

interface ChooseNextStep {

    fun isAvailableForCurrentStep(stepCode: StepCode): Boolean

    fun getNextStep(chatId: Long): StepCode?
}
