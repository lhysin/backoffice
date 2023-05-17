package io.backoffice.controller

import io.backoffice.model.res.AdminRes
import io.backoffice.service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
    private val adminService: AdminService,
) {

    @GetMapping("/api/v1/admins/{adminId}")
    fun findById(@PathVariable("adminId") adminId: String): AdminRes {
        return adminService.findById(adminId);
    }

    @GetMapping("/api/v1/admins")
    fun findAll(): List<AdminRes> {
        return adminService.findAll();
    }

}