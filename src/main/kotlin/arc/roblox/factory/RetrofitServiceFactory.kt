package arc.roblox.factory

import okhttp3.OkHttpClient

interface RetrofitServiceFactory {
    fun <T> createService(
        serviceClass: Class<T>,
        baseUrl: String,
        client: OkHttpClient
    ): T
}
