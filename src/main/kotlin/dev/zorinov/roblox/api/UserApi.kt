package dev.zorinov.roblox.api

import dev.zorinov.roblox.model.RobloxUser
import retrofit2.Call
import retrofit2.http.GET

interface UserApi {

    @GET("v1/users/authenticated")
    fun getCurrentUser(): Call<RobloxUser>

}