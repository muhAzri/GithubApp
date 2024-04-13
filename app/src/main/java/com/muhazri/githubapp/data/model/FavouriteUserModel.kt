package com.muhazri.githubapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favourite_users")
@Parcelize
data class FavouriteUserModel(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String
): Parcelable
