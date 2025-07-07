package arc.roblox.factory.impl

import arc.roblox.cookie.RobloxCookieJar
import arc.roblox.factory.HttpClientFactory
import arc.roblox.interceptor.CsrfTokenInterceptor
import arc.roblox.interceptor.impl.RobloxCsrfTokenProvider
import okhttp3.OkHttpClient

class DefaultHttpClientFactory(
    val robloxSecurity: String
) : HttpClientFactory {

    override fun createClient(): OkHttpClient {
        val cookieJar = RobloxCookieJar.create(robloxSecurity)

        val tokenClient = OkHttpClient.Builder()
            .cookieJar(cookieJar)
            .build()

        val csrfTokenInterceptor = CsrfTokenInterceptor(
            RobloxCsrfTokenProvider(tokenClient)
        )

        return tokenClient.newBuilder()
            .cookieJar(cookieJar)
            .addInterceptor(csrfTokenInterceptor)
            .build()
    }
}
