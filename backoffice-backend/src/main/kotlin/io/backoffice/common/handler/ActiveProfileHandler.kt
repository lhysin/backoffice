package io.backoffice.common.handler

import io.backoffice.common.model.type.ActiveProfile
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ActiveProfileHandler (
    @Value("\${spring.profiles.active:}")
    activeProfile: String
) {
    val activeProfile: ActiveProfile = ActiveProfile.fromString(activeProfile)

    fun isDefaultProfile() : Boolean {
        return ActiveProfile.DEFAULT == activeProfile
    }

    fun isDevelopmentProfile() : Boolean {
        return ActiveProfile.DEVELOPMENT == activeProfile
    }

    fun isProductionProfile() : Boolean {
        return ActiveProfile.PRODUCTION == activeProfile
    }
}