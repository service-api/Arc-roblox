package dev.zorinov.roblox.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class CsrfTokenInterceptor(val tokenProvider: CsrfTokenProvider) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        if (originalRequest.method != "POST") {
            return chain.proceed(originalRequest)
        }

        val newRequest = originalRequest.newBuilder()
            .header("x-csrf-token", tokenProvider.getToken())
            .build()

        val response = chain.proceed(newRequest)

        if (response.code == 403) {
            tokenProvider.invalidateToken()
            response.close()

            val retryRequest = originalRequest.newBuilder()
                .header("x-csrf-token", tokenProvider.getToken())
                .build()

            return chain.proceed(retryRequest)
        }

        return response
    }
}
