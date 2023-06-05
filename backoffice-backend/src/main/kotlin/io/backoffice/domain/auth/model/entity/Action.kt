package io.backoffice.domain.auth.model.entity

import io.backoffice.domain.auth.model.type.ActionType
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "ACTIONS")
class Action (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    var actionId: Long = 0,

    @Column(nullable = false)
    val userId: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val actionType: ActionType,

    @Column(nullable = false)
    @CreatedDate
    var createdDate: LocalDateTime = LocalDateTime.now(),

)