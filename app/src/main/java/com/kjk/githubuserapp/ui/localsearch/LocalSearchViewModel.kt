package com.kjk.githubuserapp.ui.localsearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.repo.GithubUserRepository
import com.kjk.githubuserapp.ui.remotesearch.ApiStatus
import kotlinx.coroutines.launch

class LocalSearchViewModel : ViewModel() {


    private val repository = GithubUserRepository.getInstance()


    var favoriteUsers: LiveData<List<GithubUserVO>> = repository.getFavoriteUserFromLocal()


    private val _apiStatus = MutableLiveData<ApiStatus>()
    val apiStatus: LiveData<ApiStatus>
        get() = _apiStatus


    private val _searchKeyword = MutableLiveData<String?>()
    val serachKeyword: LiveData<String?>
        get() = _searchKeyword


    private val _showMessageEvent = MutableLiveData<Boolean>()
    val showMessageEvent: LiveData<Boolean>
        get() = _showMessageEvent



    private fun searchUsers(searchKeyword: String) {
        viewModelScope.launch {
            try {
                //_users.value = repository.getFavoriteUserFromLocal(searchKeyword)
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
            //searchUsers(_searchKeyword.value ?: "")
        }
    }

    fun deleteFavoriteUser(githubUserVO: GithubUserVO) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(githubUserVO)
        }
    }


    companion object {
        private const val TAG = "LocalSearchViewModel"
    }
}