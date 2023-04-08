package com.sage.bot.repository.entity

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    var id: Long,
    var userName: String?,
    var firstName: String?,
    var lastName: String?,
    val registerAt: Instant,
    var stepCode: String,
    var text: String?,
    var accept: String?,
    ) {
    constructor() : this(
        0L,
        null,
        null,
        null,
        Instant.now(),
        "",
        null,
        null
    )
}