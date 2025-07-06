package dev.zorinov.roblox.interceptor

interface CsrfTokenProvider {
    fun getToken(): String
    fun invalidateToken()
}
