package com.muhazri.githubapp

import android.content.Context
import com.muhazri.githubapp.data.dao.FavouriteUserDao
import com.muhazri.githubapp.data.database.FavouriteUserDatabase
import com.muhazri.githubapp.data.datasources.UserLocalDataSources
import com.muhazri.githubapp.data.datasources.UserRemoteDataSource
import com.muhazri.githubapp.data.viewmodel.FavouriteViewModel
import com.muhazri.githubapp.data.viewmodel.HomeViewModel
import com.muhazri.githubapp.data.viewmodel.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Injector {

    @Singleton
    @Provides
    fun provideFavouriteUserDao(@ApplicationContext context: Context): FavouriteUserDao {
        return FavouriteUserDatabase.getInstance(context).favouriteUserDao()
    }

    @Singleton
    @Provides
    fun provideUserRemoteDataSource(): UserRemoteDataSource {
        return UserRemoteDataSource()
    }
    @Singleton
    @Provides
    fun provideLocalRemoteDataSources(
        @ApplicationContext context: Context,
        favouriteUserDao: FavouriteUserDao
    ): UserLocalDataSources {
        return UserLocalDataSources(context, favouriteUserDao)
    }

    @Singleton
    @Provides
    fun provideHomeViewModel(remoteDataSource: UserRemoteDataSource): HomeViewModel {
        return HomeViewModel(userRemoteDataSource = remoteDataSource)
    }

    @Singleton
    @Provides
    fun provideProfileViewModel(remoteDataSource: UserRemoteDataSource, localDataSource: UserLocalDataSources): ProfileViewModel {
        return ProfileViewModel(userRemoteDataSource = remoteDataSource,  localDataSource)
    }

    @Singleton
    @Provides
    fun provideFavouriteViewModel(localDataSource: UserLocalDataSources): FavouriteViewModel {
        return FavouriteViewModel( localDataSource)
    }
}