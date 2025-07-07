package arc.roblox.interceptor

interface CsrfTokenProvider {
    fun getToken(): String
    fun invalidateToken()
}
