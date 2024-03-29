package com.singidunum.authservice.controllers

import com.singidunum.authservice.services.AuthorizationService
import kotlinx.serialization.json.JsonObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthorizationController(
    private val authorizationService: AuthorizationService
) {
    @GetMapping("authorize")
    fun authorize(): Boolean = true

    @PostMapping("register")
    fun register(@RequestBody body: JsonObject): JsonObject =
        authorizationService.createUser(body)
}