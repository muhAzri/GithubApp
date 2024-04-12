package com.muhazri.githubapp.data.viewmodel

import androidx.lifecycle.ViewModel
import com.muhazri.githubapp.data.datasources.UserLocalDataSources
import com.muhazri.githubapp.data.model.FavouriteUserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(DelicateCoroutinesApi::class)
@HiltViewModel
class FavouriteViewModel @Inject constructor(private val userLocalDataSources: UserLocalDataSources) : ViewModel() {

    private val _state = MutableStateFlow(FavouriteScreenState())
    val state: StateFlow<FavouriteScreenState> = _state

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getFavouriteUsers(){
        GlobalScope.launch {
            val response = try {
                userLocalDataSources.getAllFavouriteUsers()
            } catch (e: Exception){
                _errorMessage.value = "Failed to get favourite data"
                null
            }


            response?.let {
                _state.value =_state.value.copy(favouriteUsers = it)
            }
        }
    }

    fun resetErrorData(){
        _errorMessage.value = null
    }

}

data class FavouriteScreenState(
    val favouriteUsers: List<FavouriteUserModel> = emptyList(),
    val query: String = ""
)