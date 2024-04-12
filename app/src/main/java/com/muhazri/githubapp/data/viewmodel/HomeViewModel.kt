package com.muhazri.githubapp.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muhazri.githubapp.data.datasources.UserRemoteDataSource
import com.muhazri.githubapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRemoteDataSource: UserRemoteDataSource) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun submitSearchQuery(query: String){
        viewModelScope.launch {
            val response = try {
                userRemoteDataSource.searchUsers(query)
            } catch (e: Exception) {
                _errorMessage.value = "Failed to fetch data"
                null
            }

            response?.let {
                _state.value = _state.value.copy(users=it.items)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _state.value = _state.value.copy(query = query)
    }

    fun resetErrorData(){
        _errorMessage.value = null
    }

}

data class HomeScreenState(
    val users: List<User> = emptyList(),
    val query: String = ""
)
