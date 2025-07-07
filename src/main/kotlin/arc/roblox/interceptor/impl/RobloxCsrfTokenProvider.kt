package arc.roblox.interceptor.impl

import arc.roblox.interceptor.CsrfTokenProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class RobloxCsrfTokenProvider(val client: OkHttpClient) : CsrfTokenProvider {
    @Volatile
    var csrfToken: String? = null

    @Synchronized
    override fun getToken(): String {
        if (csrfToken != null) return csrfToken!!

        val request = Request.Builder()
            .url("https://auth.roblox.com/v2/logout")
            .post("".toRequestBody(null))
            .build()

        client.newCall(request).execute().use { response ->
            val token = response.header("x-csrf-token")
                ?: throw IllegalStateException("CSRF token not found")
            csrfToken = token
            return token
        }
    }

    @Synchronized
    override fun invalidateToken() {
        csrfToken = null
    }
}
