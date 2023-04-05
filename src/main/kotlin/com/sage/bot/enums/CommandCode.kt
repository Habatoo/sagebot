package com.sage.bot.enums

enum class CommandCode(val command: String, val desc: String) {
    START("start", "Start work"),
    USER_INFO("user_info", "User info"),
    ASK("ask", "Ask question"),
    HELP("help", "Help info"),
    BUTTON("button", "Button YES NO")
}
