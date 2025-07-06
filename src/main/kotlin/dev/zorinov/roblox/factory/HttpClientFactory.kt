package dev.zorinov.roblox.factory

import okhttp3.OkHttpClient

interface HttpClientFactory {
    fun createClient(): OkHttpClient
}
