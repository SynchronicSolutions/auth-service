package com.singidunum.authservice.networking

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

const val TIMEOUT_SECONDS: Long = 100

open class ApiService : Api {
    private val client by lazy {
        val clientBuilder = OkHttpClient.Builder().apply {
            readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
        }

        val defaultHttpInterceptor = HttpLoggingInterceptor()
        clientBuilder.addInterceptor(defaultHttpInterceptor)

        clientBuilder.build()
    }

    override fun getRetrofit(baseUrl: String): Retrofit {
        val json = Json {
            ignoreUnknownKeys = true
        }

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}