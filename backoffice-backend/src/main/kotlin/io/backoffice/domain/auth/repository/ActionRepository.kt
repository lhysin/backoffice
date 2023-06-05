package io.backoffice.domain.auth.repository

import io.backoffice.domain.auth.model.entity.Action
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ActionRepository : JpaRepository<Action, Long> {
}