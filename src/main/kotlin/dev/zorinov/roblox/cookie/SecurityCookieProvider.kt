package dev.zorinov.roblox.cookie

import okhttp3.Cookie

interface SecurityCookieProvider {
    fun getSecurityCookie(): Cookie
}
