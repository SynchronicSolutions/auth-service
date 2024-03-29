package com.singidunum.authservice.services

import com.singidunum.authservice.networking.ApiService
import com.singidunum.authservice.networking.users.UsersApiService
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthorizationService(
    private val usersApiService: UsersApiService
) {
    fun createUser(body: JsonObject): JsonObject {
        val jsonPassword = body["password"]

        val bodyWithSecurePassword =
            if (jsonPassword != null) {
                JsonObject(
                    body.toMutableMap().apply {
                        val securePassword = BCryptPasswordEncoder().encode(jsonPassword.jsonPrimitive.content)
                        put("password", JsonPrimitive(securePassword))
                    }
                )
            } else {
                body
            }

        return usersApiService.createUser(bodyWithSecurePassword)
    }

}