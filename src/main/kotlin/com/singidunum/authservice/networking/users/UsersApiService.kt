package com.singidunum.authservice.networking.users

import com.singidunum.authservice.networking.ApiService
import com.singidunum.authservice.networking.RoutingEndpointException
import com.singidunum.authservice.networking.create
import com.singidunum.authservice.util.RequestUriUtil.getServiceUrl
import kotlinx.serialization.json.JsonObject
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

private const val USERS_SERVICE_URL = "users"

@Service
class UsersApiService(
    private val discoveryClient: DiscoveryClient
): ApiService() {
    val usersEndpoints: UsersEndpoints by lazy {
        create(getUsersServiceUrl())
    }

    fun createUser(body: JsonObject): JsonObject {
        val response = usersEndpoints.createUser(body).execute()

        val responseBody = response.body()

        if (response.errorBody() != null || responseBody == null) {
            throw IllegalStateException("Creating user ${body["email"]} failed")
        }

        return responseBody
    }

    fun getUserByEmail(email: String): JsonObject {
        val response = usersEndpoints.getUsersByEmail(email).execute()

        val responseBody = response.body()

        if (response.errorBody() != null || responseBody == null) {
            throw IllegalStateException("User with email $email doesn't exist")
        }

        return responseBody
    }

    private fun getUsersServiceUrl() = discoveryClient.getServiceUrl(USERS_SERVICE_URL)
        ?: throw RoutingEndpointException("Service $USERS_SERVICE_URL not available")
}