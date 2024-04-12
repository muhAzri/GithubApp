package com.muhazri.githubapp.data.viewmodel

import androidx.lifecycle.ViewModel
import com.muhazri.githubapp.data.datasources.UserLocalDataSources
import com.muhazri.githubapp.data.datasources.UserRemoteDataSource
import com.muhazri.githubapp.data.model.DetailUserModel
import com.muhazri.githubapp.data.model.FavouriteUserModel
import com.muhazri.githubapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSources
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    @OptIn(DelicateCoroutinesApi::class)
    fun getUserDetail(username: String){
        GlobalScope.launch {

            _state.value = _state.value.copy(userDetailLoading = true)

            val response = try {
                userRemoteDataSource.getUserDetail(username)
            } catch (e: Exception){
                _errorMessage.value = "Failed to fetch User Detail"
                null
            }

            response?.let {
                _state.value =_state.value.copy(detailUser = it, userDetailLoading = false)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getUserFollowers(username: String){
        GlobalScope.launch {

            _state.value = _state.value.copy(userFollowersLoading = true)

            val response = try {
                userRemoteDataSource.getUserFollower(username)
            } catch (e: Exception){
                _errorMessage.value = "Failed to fetch User Followers"
                null
            }

            response?.let {
                _state.value =_state.value.copy(followers = it, userFollowersLoading = false)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getUserFollowing(username: String){
        GlobalScope.launch {

            _state.value = _state.value.copy(userFollowingLoading = true)

            val response = try {
                userRemoteDataSource.getUserFollowing(username)
            } catch (e: Exception){
                _errorMessage.value = "Failed to fetch User Following"
                null
            }

            response?.let {
                _state.value =_state.value.copy(following = it, userFollowingLoading = false)
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getIsFavorite(username: String){
        GlobalScope.launch {

            val isFavourite = try {
                userLocalDataSource.isUserExists(username)
            }catch (e: Exception){
                _errorMessage.value = "Failed to fetch User Favourite Status"
                null
            }


            isFavourite?.let {
                _state.value = _state.value.copy(isFavourite = it)
            }

        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun setIsFavorite(user: FavouriteUserModel){
        GlobalScope.launch {
            try {
                if(userLocalDataSource.isUserExists(user.login)){
                    userLocalDataSource.deleteFavouriteUser(user)
                } else {
                    userLocalDataSource.saveFavouriteUser(user)
                }
            } catch (e: Exception){
                _errorMessage.value = "Failed to Set User Favourite Status"
            }

            val isFavourite = try {
                userLocalDataSource.isUserExists(user.login)
            }catch (e: Exception){
                _errorMessage.value = "Failed to fetch User Favourite Status"
                null
            }

            isFavourite?.let {
                _state.value = _state.value.copy(isFavourite = it)
            }

        }
    }



    fun resetErrorData(){
        _errorMessage.value = null
    }

    fun resetState() {
        _state.value = ProfileScreenState()
    }


}

data class ProfileScreenState(
    val detailUser: DetailUserModel? = null,
    val followers: List<User> = emptyList(),
    val following: List<User> = emptyList(),
    val isFavourite: Boolean = false,
    val userDetailLoading: Boolean = false,
    val userFollowingLoading: Boolean = false,
    val userFollowersLoading: Boolean = false,
)
