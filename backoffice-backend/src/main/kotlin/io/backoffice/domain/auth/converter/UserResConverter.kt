package io.backoffice.domain.auth.converter

import io.backoffice.domain.auth.model.entity.User
import io.backoffice.domain.auth.model.res.UserRes
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class UserResConverter : Converter<User, UserRes> {
    override fun convert(user: User): UserRes {
        return UserRes(
            userId = user.userId,
            name = user.name,
            email = user.email,
            contactNumber = user.contactNumber
        )
    }
}