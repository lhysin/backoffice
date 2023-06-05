package io.backoffice.domain.auth.service

import io.backoffice.domain.auth.model.entity.Action
import io.backoffice.domain.auth.model.type.ActionType
import io.backoffice.domain.auth.repository.ActionRepository
import org.springframework.stereotype.Service

@Service
class ActionService(
    val actionRepository: ActionRepository
) {

    fun saveAction(
        userId : String,
        actionType : ActionType
    ) {
        actionRepository.save(
            Action(
                userId = userId,
                actionType = actionType,
            )
        )
    }
}