package com.sage.bot.service

import com.sage.bot.enums.StepCode
import com.sage.bot.repository.UsersRepository
import com.sage.bot.repository.entity.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.Optional

@Service
class UserService(
    private val usersRepository: UsersRepository,
) {
    private val log = LoggerFactory.getLogger(UserService::class.java)

    fun isUserExist(chatId: Long): Boolean {
        return usersRepository.existsById(chatId)
    }

    fun createUser(chatId: Long): UserEntity {
        val userEntity = UserEntity(
            id = chatId,
            stepCode = StepCode.START.toString(),
            text = null,
            accept = null,
            registerAt = Instant.now()
        )
        usersRepository.save(userEntity)
        return userEntity
    }

    fun getUser(chatId: Long): Optional<UserEntity> {
        return usersRepository.findById(chatId)
    }

    fun updateUserStep(chatId: Long, stepCode: StepCode) {
        update(chatId, "stepCode", stepCode.name)
    }

    fun updateText(chatId: Long, text: String) {
        update(chatId, "text", text)
    }

    fun updateAccept(chatId: Long, accept: String) {
        update(chatId, "accept", accept)
    }

    fun update(chatId: Long, field: String, value: String) {
        val userEntityOptional = usersRepository.findById(chatId)
        if (userEntityOptional.isPresent) {
            val userEntity = userEntityOptional.get()
            when (field) {
                "stepCode" -> userEntity.stepCode = value
                "text" -> userEntity.text = value
                "accept" -> userEntity.accept = value
                else -> log.error("Invalid field: $field")
            }
            usersRepository.save(userEntity)
        } else {
            log.error("For $chatId there is no user.")
        }
    }

}
