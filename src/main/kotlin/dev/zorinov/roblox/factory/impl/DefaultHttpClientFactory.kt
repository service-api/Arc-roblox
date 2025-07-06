package dev.zorinov.roblox.factory.impl

import dev.zorinov.roblox.cookie.RobloxCookieJar
import dev.zorinov.roblox.factory.HttpClientFactory
import dev.zorinov.roblox.interceptor.CsrfTokenInterceptor
import dev.zorinov.roblox.interceptor.impl.RobloxCsrfTokenProvider
import okhttp3.OkHttpClient

class DefaultHttpClientFactory(
    val robloxSecurity: String
) : HttpClientFactory {

    override fun createClient(): OkHttpClient {
        val cookieJar = RobloxCookieJar.create(robloxSecurity)

        val tokenClient = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()

        val csrfTokenInterceptor = CsrfTokenInterceptor(RobloxCsrfTokenProvider(tokenClient))

        return tokenClient.newBuilder()
            .cookieJar(cookieJar)
            .addInterceptor(csrfTokenInterceptor)
            .build()
    }
}
