package arc.roblox.api

import arc.roblox.model.FriendsResponse
import retrofit2.Call
import retrofit2.http.*

interface FriendApi {

    @GET("v1/users/{userId}/friends")
    fun getUserFriends(@Path("userId") userId: Long): Call<FriendsResponse>

    @POST("v1/users/{userId}/request-friendship")
    @Headers("Content-Type: application/json;charset=UTF-8")
    fun requestFriendship(
        @Path("userId") userId: Long,
        @Body body: Map<String, String> = mapOf("friendshipOriginSourceType" to "PlayerSearch")
    ): Call<Unit>

}