
package dev.zorinov.roblox

import dev.zorinov.roblox.api.FriendApi
import dev.zorinov.roblox.api.UserApi
import dev.zorinov.roblox.factory.HttpClientFactory
import dev.zorinov.roblox.factory.RetrofitServiceFactory
import dev.zorinov.roblox.factory.impl.DefaultHttpClientFactory
import dev.zorinov.roblox.factory.impl.DefaultRetrofitServiceFactory
import dev.zorinov.roblox.model.FriendsResponse
import dev.zorinov.roblox.model.RobloxUser

class RobloxClient(
    val userApi: UserApi,
    val friendApi: FriendApi
) {
    fun getCurrentUser(): RobloxUser? {
        return userApi.getCurrentUser().execute().let { response ->
            if (response.isSuccessful) response.body() else null
        }
    }

    fun getUserFriends(userId: Long): FriendsResponse {
        return friendApi.getUserFriends(userId).execute().body() ?: FriendsResponse(emptyList())
    }

    fun requestFriendship(userId: Long): Boolean {
        return friendApi.requestFriendship(userId).execute().isSuccessful
    }

    class Builder(private val robloxSecurity: String) {
        var httpClientFactory: HttpClientFactory? = null
        var retrofitServiceFactory: RetrofitServiceFactory? = null

        fun withHttpClientFactory(factory: HttpClientFactory) = apply {
            this.httpClientFactory = factory
        }

        fun withRetrofitServiceFactory(factory: RetrofitServiceFactory) = apply {
            this.retrofitServiceFactory = factory
        }

        fun build(): RobloxClient {
            val clientFactory = httpClientFactory ?: DefaultHttpClientFactory(robloxSecurity)
            val serviceFactory = retrofitServiceFactory ?: DefaultRetrofitServiceFactory.builder().build()

            val httpClient = clientFactory.createClient()

            val userApi = serviceFactory.createService(
                UserApi::class.java,
                "https://users.roblox.com/",
                httpClient
            )

            val friendApi = serviceFactory.createService(
                FriendApi::class.java,
                "https://friends.roblox.com/",
                httpClient
            )

            return RobloxClient(userApi, friendApi)
        }
    }

    companion object {
        fun builder(robloxSecurity: String) = Builder(robloxSecurity)
    }
}