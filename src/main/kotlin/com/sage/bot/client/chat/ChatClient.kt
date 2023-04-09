package com.sage.bot.client.chat

import com.chat.grpc.ChatServiceGrpc
import com.chat.grpc.ChatServiceOuterClass
import com.sage.bot.dto.AskDto
import com.sage.bot.properties.BotProperty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.stereotype.Component

@Component
class ChatClient(
    private val botProperty: BotProperty,
) {

    fun ask(chatId: Long, question: String): AskDto {
        val url = "${botProperty.chatHost}:${botProperty.chatPort}"
        val channel: ManagedChannel = ManagedChannelBuilder
            .forTarget(url)
            .usePlaintext()
            .build()

        val stub: ChatServiceGrpc.ChatServiceBlockingStub = ChatServiceGrpc.newBlockingStub(channel)
        val request = ChatServiceOuterClass.ChatRequest
            .newBuilder()
            .setChatId(chatId)
            .setQuestion(question)
            .build()

        val response = stub.ask(request)
        channel.shutdownNow()

        return AskDto(response.chatId, response.answer)
    }

}