package com.sage.bot.repository.entity

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    var id: Long,

    var stepCode: String,

    var text: String?,

    var accept: String?,

    val registerAt: Instant,
) {
    constructor() : this(0L, "", null, null, Instant.now())
}