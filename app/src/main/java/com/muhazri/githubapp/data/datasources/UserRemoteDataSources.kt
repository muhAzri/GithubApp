package com.muhazri.githubapp.data.datasources

import com.muhazri.githubapp.data.model.DetailUserModel
import com.muhazri.githubapp.data.model.User
import com.muhazri.githubapp.data.model.UserSearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") username: String): UserSearchResponse

    @GET("users/{username}")
    suspend fun getUserDetail(@Path("username") username: String): DetailUserModel

    @GET("users/{username}/followers")
    suspend fun getUserFollowers(@Path("username") username: String): List<User>

    @GET("users/{username}/following")
    suspend fun getUserFollowing(@Path("username") username: String): List<User>
}

class UserRemoteDataSource {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(GitHubService::class.java)

    suspend fun searchUsers(username: String): UserSearchResponse {
        return service.searchUsers(username)
    }

    suspend fun getUserDetail(username: String): DetailUserModel {
        return service.getUserDetail(username)
    }

    suspend fun getUserFollower(username: String): List<User> {
        return service.getUserFollowers(username)
    }

    suspend fun getUserFollowing(username: String): List<User> {
        return service.getUserFollowing(username)
    }
}
