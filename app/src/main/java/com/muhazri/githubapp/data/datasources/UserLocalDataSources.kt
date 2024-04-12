package com.muhazri.githubapp.data.datasources

import android.content.Context
import com.muhazri.githubapp.data.dao.FavouriteUserDao
import com.muhazri.githubapp.data.database.FavouriteUserDatabase
import com.muhazri.githubapp.data.model.FavouriteUserModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class UserLocalDataSources @Inject constructor(
    @ApplicationContext context: Context,
    private var favouriteUserDao: FavouriteUserDao
) {

    init {
        val db = FavouriteUserDatabase.getInstance(context)
        favouriteUserDao = db.favouriteUserDao()
    }

    suspend fun getAllFavouriteUsers(): List<FavouriteUserModel> {
        return favouriteUserDao.getAll()
    }

    suspend fun saveFavouriteUser(user: FavouriteUserModel) {
            favouriteUserDao.insert(user = user)
    }

    suspend fun deleteFavouriteUser(user: FavouriteUserModel) {
        favouriteUserDao.delete(user)
    }

    suspend fun isUserExists(login: String): Boolean {
        return favouriteUserDao.isUserExists(login)
    }
}
