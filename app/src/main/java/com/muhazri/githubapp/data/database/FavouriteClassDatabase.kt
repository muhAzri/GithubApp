package com.muhazri.githubapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muhazri.githubapp.data.dao.FavouriteUserDao
import com.muhazri.githubapp.data.model.FavouriteUserModel

@Database(entities = [FavouriteUserModel::class], version = 1)
abstract class FavouriteUserDatabase : RoomDatabase() {

    abstract fun favouriteUserDao(): FavouriteUserDao

    companion object {
        @Volatile
        private var instance: FavouriteUserDatabase? = null

        fun getInstance(context: Context): FavouriteUserDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): FavouriteUserDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                FavouriteUserDatabase::class.java, "favourite_users.db"
            ).build()
        }
    }
}
