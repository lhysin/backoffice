package io.backoffice.domain.auth.repository

import io.backoffice.domain.auth.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String> {
    fun findByUserIdAndDeletedDateIsNullAndDeletedByIsNull(userId : String) : User?
}