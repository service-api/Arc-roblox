package arc.roblox.factory

import okhttp3.OkHttpClient

interface HttpClientFactory {
    fun createClient(): OkHttpClient
}
