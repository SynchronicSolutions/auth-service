package com.singidunum.authservice.security

import com.singidunum.authservice.networking.AuthorizationException
import com.singidunum.authservice.networking.users.UsersApiService
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val usersApiService: UsersApiService
): UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        if (username != null) {
            val userObject = usersApiService.getUserByEmail(username)

            val email = requireNotNull(userObject["email"]?.jsonPrimitive?.content)
            val password = requireNotNull(userObject["passwordHash"]?.jsonPrimitive?.content)

            return User(
                email = email,
                passwordHash = password
            )
        }

        throw AuthorizationException("User not found.")
    }
}