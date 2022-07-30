package com.kjk.githubuserapp.ui.remotesearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjk.githubuserapp.domain.UserEntity
import com.kjk.githubuserapp.repo.GithubUserRepository
import kotlinx.coroutines.launch

class RemoteSearchViewModel : ViewModel() {

    private val repository = GithubUserRepository()

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>>
        get() = _users

    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            try {
                _users.value = repository.getUsersFromNetwork()
                _apiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                Log.d(TAG, "loadUsers: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "RemoteSearchViewModel"
    }
}

enum class ApiStatus {
    LOADING,
    DONE,
    ERROR
}