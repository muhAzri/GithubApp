package com.muhazri.githubapp.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserModel(
    val id: Int,
    val login:String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String,
    val location: String,
    val followers: Int,
)
