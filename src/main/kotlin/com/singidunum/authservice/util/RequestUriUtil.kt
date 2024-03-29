package com.singidunum.authservice.util

import org.springframework.cloud.client.discovery.DiscoveryClient

object RequestUriUtil {
    fun DiscoveryClient.getServiceUrl(serviceName: String): String? =
        this
            .getInstances(serviceName)
            .firstOrNull()
            ?.uri
            ?.toString()

    fun String.getRequestUriParts(): List<String> =
        this
            .substring(1, this.length)
            .split("/")

    fun String.getServiceName(): String? =
        getRequestUriParts().firstOrNull()
}