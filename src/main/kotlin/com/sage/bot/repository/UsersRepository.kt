package com.sage.bot.repository

import com.sage.bot.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository: JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    override fun existsById(id: Long): Boolean
}
