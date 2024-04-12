package com.muhazri.githubapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.muhazri.githubapp.data.model.FavouriteUserModel

@Dao
interface FavouriteUserDao {

    @Query("SELECT * FROM favourite_users")
    suspend fun getAll(): List<FavouriteUserModel>

    @Insert
    suspend fun insert(user: FavouriteUserModel)

    @Delete
    suspend fun delete(user: FavouriteUserModel)

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_users WHERE login = :login LIMIT 1)")
    suspend fun isUserExists(login: String): Boolean
}
