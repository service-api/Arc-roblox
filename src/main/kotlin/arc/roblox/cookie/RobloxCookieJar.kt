package arc.roblox.cookie

import arc.roblox.cookie.impl.InMemoryCookieStorage
import arc.roblox.cookie.impl.RobloxSecurityCookieProvider
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class RobloxCookieJar(
    val cookieStorage: CookieStorage,
    val securityCookieProvider: SecurityCookieProvider
) : CookieJar {

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        cookieStorage.saveCookies(cookies)
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStorage.loadCookies(url) + securityCookieProvider.getSecurityCookie()
    }

    companion object {
        fun create(robloxSecurity: String): RobloxCookieJar {
            return RobloxCookieJar(
                cookieStorage = InMemoryCookieStorage(),
                securityCookieProvider = RobloxSecurityCookieProvider(
                    robloxSecurity
                )
            )
        }
    }
}

