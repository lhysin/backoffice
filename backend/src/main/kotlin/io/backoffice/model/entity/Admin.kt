package io.backoffice.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "ADMIN")
class Admin (

    @Id
    @Column(nullable = false)
    val adminId: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val passwordExpired: Boolean,

    @Column(nullable = false)
    @CreatedBy
    val createdBy: String? = null,

    @Column(nullable = true)
    @CreatedDate
    val createdDate: LocalDateTime? = null,

    @Column(nullable = true)
    @LastModifiedBy
    val lastModifiedBy: String? = null,

    @Column(nullable = true)
    @LastModifiedDate
    val lastModifiedDate: LocalDateTime? = null,
)