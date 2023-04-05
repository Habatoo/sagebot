package com.sage.bot.dto

data class HelpDto(
    val helpInfo: String =             """
                Я бот, советник, отвечаю на вопросы.  
                
                ❗*Список команд*
                /start - начать
                /ask - задать вопрос боту (вопрос нужно писать после команды /ask в одном сообщении, 
                если команда /ask отправлена пустой, то вопрос можно написать в следущем сообщении)
                /help - помощь
                
                Задай вопрос и получишь ответ 
                👉 Например, USER: /ask Как поднять денег?
    
                Желаю удачи🙂
                """
)