package io.backoffice.common.model.type

enum class ActiveProfile {
    DEFAULT,
    DEVELOPMENT,
    PRODUCTION,
;
    companion object {
        fun fromString(profile: String): ActiveProfile {
            return try {
                valueOf(profile.uppercase())
            } catch (e: IllegalArgumentException) {
                DEFAULT
            }
        }
    }
}