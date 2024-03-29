package com.singidunum.authservice.networking.users

import com.singidunum.authservice.networking.EndPoints
import kotlinx.serialization.json.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UsersEndpoints: EndPoints {
    @POST("/api/users")
    fun createUser(
        @Body body: JsonObject
    ): Call<JsonObject>

    @GET("/api/user-digest")
    fun getUsersByEmail(
        @Query("email") email: String
    ): Call<JsonObject>
}