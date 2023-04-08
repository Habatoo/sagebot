package com.sage.bot.strategy.logic

interface Chooser {

    fun isAvailableForCurrentStep(chatId: Long): Boolean

    fun isPermitted(chatId: Long): Boolean

}