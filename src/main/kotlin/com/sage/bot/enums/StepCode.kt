package com.sage.bot.enums

enum class StepCode(val type: StepType, val botPause: Boolean) {
    START(StepType.SIMPLE_TEXT, false),
    USER_INFO(StepType.SIMPLE_TEXT, true),
    CLEAN_MY_INFO(StepType.SIMPLE_TEXT, true),
    CLEAN_MY_INFO_BUTTON_REQUEST(StepType.INLINE_KEYBOARD_MARKUP, true),
    CLEAN_MY_INFO_BUTTON_RESPONSE(StepType.SIMPLE_TEXT, true),
    ASK(StepType.SIMPLE_TEXT, true),
    HELP(StepType.SIMPLE_TEXT, true), // todo false
    BUTTON_REQUEST(StepType.INLINE_KEYBOARD_MARKUP, true),
    BUTTON_RESPONSE(StepType.SIMPLE_TEXT, true),
}

enum class StepType {
    SIMPLE_TEXT,
    INLINE_KEYBOARD_MARKUP
}
