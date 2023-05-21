package io.backoffice.converter

import io.backoffice.model.entity.Admin
import io.backoffice.model.res.AdminRes
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class AdminResConverter : Converter<Admin, AdminRes> {
    override fun convert(admin: Admin): AdminRes {
        return AdminRes(
            adminId = admin.adminId,
            passwordExpired = admin.passwordExpired,
            createdBy = admin.createdBy,
            createdDate = admin.createdDate,
            lastModifiedBy = admin.lastModifiedBy,
            lastModifiedDate = admin.lastModifiedDate,
        );
    }
}