package dev.zorinov.roblox

import dev.zorinov.roblox.api.FriendApi
import dev.zorinov.roblox.api.UserApi
import dev.zorinov.roblox.model.FriendData
import dev.zorinov.roblox.model.FriendsResponse
import dev.zorinov.roblox.model.RobloxUser
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Call
import retrofit2.Response

class RobloxClientTest {

    lateinit var userApi: UserApi
    lateinit var friendApi: FriendApi
    lateinit var client: RobloxClient

    @BeforeEach
    fun setUp() {
        userApi = mockk()
        friendApi = mockk()
        client = RobloxClient(userApi, friendApi)
    }

    @Test
    fun `getCurrentUser should return user on successful request`() {
        val expectedUser = RobloxUser(1L, "test", "Test User")
        val call: Call<RobloxUser> = mockk()
        val response: Response<RobloxUser> = Response.success(expectedUser)

        every { userApi.getCurrentUser() } returns call
        every { call.execute() } returns response

        val result = client.getCurrentUser()

        assertThat(result).isEqualTo(expectedUser)
    }

    @Test
    fun `getUserFriends should return friends list`() {
        val friends = listOf(
            FriendData(1L, "friend1", "Friend One"),
            FriendData(2L, "friend2", "Friend Two")
        )
        val expectedResponse = FriendsResponse(friends)
        val call: Call<FriendsResponse> = mockk()
        val response: Response<FriendsResponse> = Response.success(expectedResponse)

        every { friendApi.getUserFriends(1L) } returns call
        every { call.execute() } returns response

        val result = client.getUserFriends(1L)

        assertThat(result).isEqualTo(expectedResponse)
    }

    @Test
    fun `requestFriendship should return true on successful request`() {
        val call: Call<Unit> = mockk()
        val response: Response<Unit> = Response.success(Unit)

        every { friendApi.requestFriendship(1L) } returns call
        every { call.execute() } returns response

        val result = client.requestFriendship(1L)

        assertThat(result).isTrue()
    }
}