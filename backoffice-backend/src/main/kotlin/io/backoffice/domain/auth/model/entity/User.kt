package io.backoffice.domain.auth.model.entity

import io.backoffice.domain.auth.model.type.UserRole
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "USERS")
class User (

    @Id
    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var passwordExpiredDate: LocalDateTime,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var contactNumber: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val userRole: UserRole,

    @Column(nullable = true)
    @CreatedBy
    var createdBy: String? = null,

    @Column(nullable = true)
    @CreatedDate
    var createdDate: LocalDateTime? = null,

    @Column(nullable = true)
    @LastModifiedBy
    var lastModifiedBy: String? = null,

    @Column(nullable = true)
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null,

    @Column(nullable = true)
    var deletedBy: String? = null,

    @Column(nullable = true)
    var deletedDate: LocalDateTime? = null,
)