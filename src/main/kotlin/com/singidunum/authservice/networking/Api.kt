package com.singidunum.authservice.networking

import retrofit2.Retrofit

interface EndPoints

interface Api {
    fun getRetrofit(baseUrl: String): Retrofit
}

inline fun <reified T : EndPoints> Api.create(baseUrl: String): T {
    return getRetrofit(baseUrl).create(T::class.java)
}