package com.kjk.githubuserapp.ui.localsearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.repo.GithubUserRepository
import kotlinx.coroutines.launch

/**
 *  LocalSearchFragment의 UI Data를 관리하는 viewModel
 *  Repository singleton객체를 통해, 필요한 data를 fetch한 후,
 *  UI controller에 보여준다.
 */
class LocalSearchViewModel : ViewModel() {


    private val repository = GithubUserRepository.getInstance()


    private val _searchKeyword = MutableLiveData<String?>()
    val searchKeyword: LiveData<String?>
        get() = _searchKeyword


    private val _showMessageEvent = MutableLiveData<Boolean>()
    val showMessageEvent: LiveData<Boolean>
        get() = _showMessageEvent


    private val _hideKeyboardEvent = MutableLiveData<Boolean>()
    val hideKeyboardEvent: LiveData<Boolean>
        get() = _hideKeyboardEvent


    private val _loadType = MutableLiveData<LoadType>()
    val loadType: LiveData<LoadType>
        get() = _loadType


    var favoriteUsers: LiveData<List<GithubUserVO>>? = null
    var searchedUsers: LiveData<List<GithubUserVO>>? = null


    init {
        loadFavoriteUsersFromLocal()
    }

    fun loadFavoriteUsersFromLocal() {
        viewModelScope.launch {
            _loadType.value = LoadType.LOAD_ALL
            try {
                favoriteUsers = repository.getFavoriteUserFromLocal()
            } catch (e: Exception) {
                Log.d(TAG, "loadFavoriteUsersFromLocal: ${e.message}")
            }
        }
    }

    private fun searchUsers(searchKeyword: String) {
        _loadType.value = LoadType.LOAD_SEARCHED_RESULT
        viewModelScope.launch {
            try {
                searchedUsers = repository.getResultFavoriteUser(searchKeyword)
            } catch (e: Exception) {
                Log.d(TAG, "loadUsers: ${e.message}")
            }
        }
    }

    fun setSearchKeyword(searchKeyword: String) {
        _searchKeyword.value = searchKeyword
    }

    fun searchButtonClickEvent() {
        if (_searchKeyword.value.isNullOrEmpty()) {
            _showMessageEvent.value = true
        } else {
            _hideKeyboardEvent.value = true
            searchUsers(_searchKeyword.value ?: "")
        }
    }

    fun deleteFavoriteUser(githubUserVO: GithubUserVO) {
        viewModelScope.launch {
            repository.deleteFavoriteUser(githubUserVO)
        }
    }

    fun showMessageEventDone() {
        _showMessageEvent.value = false
    }

    fun hideKeyboardDone() {
        _hideKeyboardEvent.value = false
    }


    companion object {
        private const val TAG = "LocalSearchViewModel"
    }
}

enum class LoadType {
    LOAD_ALL,
    LOAD_SEARCHED_RESULT
}