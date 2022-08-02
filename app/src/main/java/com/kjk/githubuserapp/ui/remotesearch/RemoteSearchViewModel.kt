package com.kjk.githubuserapp.ui.remotesearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.repo.GithubUserRepository
import kotlinx.coroutines.launch

class RemoteSearchViewModel : ViewModel() {


    private val repository = GithubUserRepository.getInstance()


    private val _users = MutableLiveData<List<GithubUserVO>>()
    val users: LiveData<List<GithubUserVO>>
        get() = _users


    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus


    private val _searchKeyword = MutableLiveData<String?>()
    val serachKeyword: LiveData<String?>
        get() = _searchKeyword


    private val _showMessageEvent = MutableLiveData<Boolean>()
    val showMessageEvent: LiveData<Boolean>
        get() = _showMessageEvent


    private val _hideKeyboardEvent = MutableLiveData<Boolean>()
    val hideKeyboardEvent: LiveData<Boolean>
        get() = _hideKeyboardEvent


    private fun searchUsers(searchKeyword: String) {
        viewModelScope.launch {
            _apiStatus.value = ApiStatus.LOADING
            try {
                _users.value = repository.getUsersFromNetwork(searchKeyword)
                _apiStatus.value = ApiStatus.DONE
            } catch (e: Exception) {
                _apiStatus.value = ApiStatus.ERROR
                Log.d(TAG, "loadUsers: ${e.message}")
            }
        }
    }


    fun setSearchKeyword(searchKeyword: String) {
        _searchKeyword.value = searchKeyword
    }


    fun searchButtonClickEvent() {
        if (_searchKeyword.value == null) {
            _showMessageEvent.value = true
        } else {
            _hideKeyboardEvent.value = true
            searchUsers(_searchKeyword.value ?: "")
        }
    }


    fun showMessageEventDone() {
        _showMessageEvent.value = false
    }


    fun deleteFavoriteUser(githubUserVO: GithubUserVO) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(githubUserVO)
        }
    }


    fun addFavoriteUser(githubUserVO: GithubUserVO) {
        viewModelScope.launch {
            repository.addFavoriteUser(githubUserVO)
        }
    }

    fun hideKeyboradDone() {
        _hideKeyboardEvent.value = false
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