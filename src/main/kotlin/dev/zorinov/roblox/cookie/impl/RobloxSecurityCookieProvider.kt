package dev.zorinov.roblox.cookie.impl

import dev.zorinov.roblox.cookie.SecurityCookieProvider
import okhttp3.Cookie

class RobloxSecurityCookieProvider(
    val securityToken: String
) : SecurityCookieProvider {
    override fun getSecurityCookie(): Cookie {
        return Cookie.Builder()
            .domain("roblox.com")
            .path("/")
            .name(".ROBLOSECURITY")
            .value(securityToken)
            .httpOnly()
            .secure()
            .build()
    }
}
