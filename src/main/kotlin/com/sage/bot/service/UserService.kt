package com.sage.bot.service

import com.sage.bot.enums.StepCode
import com.sage.bot.repository.UsersRepository
import com.sage.bot.repository.entity.UserEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.User
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

    fun createUser(chatId: Long, user: User): UserEntity {
        val userEntity = UserEntity(
            id = chatId,
            userName = user.userName,
            firstName = user.firstName,
            lastName = user.lastName,
            registerAt = Instant.now(),
            stepCode = StepCode.START.toString(),
            text = null,
            accept = null,
        )
        usersRepository.saveAndFlush(userEntity)
        log.info("Создан пользователь ${user.userName}")
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

    fun updateUserName(chatId: Long, userName: String) {
        update(chatId, "userName", userName)
    }

    fun updateFirstName(chatId: Long, firstName: String) {
        update(chatId, "firstName", firstName)
    }

    fun updateLastName(chatId: Long, lastName: String) {
        update(chatId, "lastName", lastName)
    }

    fun update(chatId: Long, field: String, value: String) {
        val userEntityOptional = usersRepository.findById(chatId)
        if (userEntityOptional.isPresent) {
            val userEntity = userEntityOptional.get()
            when (field) {
                "userName" -> userEntity.userName = value
                "firstName" -> userEntity.firstName = value
                "lastName" -> userEntity.lastName = value
                "stepCode" -> userEntity.stepCode = value
                "text" -> userEntity.text = value
                "accept" -> userEntity.accept = value
                else -> log.error("Invalid field: $field")
            }
            usersRepository.saveAndFlush(userEntity)
        } else {
            log.error("For $chatId there is no user.")
        }
    }

    fun delete(chatId: Long) {
        val userEntityOptional = usersRepository.findById(chatId)
        if (userEntityOptional.isPresent) {
            log.info("Delete user with id $chatId.")
            val userEntity = userEntityOptional.get()
            usersRepository.delete(userEntity)
        } else {
            log.error("For $chatId there is no user.")
        }
    }

}
