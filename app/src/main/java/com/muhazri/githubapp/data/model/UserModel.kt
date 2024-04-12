package com.muhazri.githubapp.data.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    val totalCount: Int,
    val incompleteResults: Boolean,
    val items: List<User>
)

data class User(
    val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
