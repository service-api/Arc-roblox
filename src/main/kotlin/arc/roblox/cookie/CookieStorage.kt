package arc.roblox.cookie

import okhttp3.Cookie
import okhttp3.HttpUrl

interface CookieStorage {
    fun saveCookies(cookies: List<Cookie>)
    fun loadCookies(url: HttpUrl): List<Cookie>
}
