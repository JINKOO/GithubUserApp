package com.kjk.githubuserapp.ui.util

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kjk.githubuserapp.domain.UserEntity
import com.kjk.githubuserapp.ui.adapter.UserAdapter
import com.kjk.githubuserapp.ui.remotesearch.ApiStatus

@BindingAdapter("user_list")
fun setUserList(recyclerView: RecyclerView, users: List<UserEntity>?) {
    val adapter = recyclerView.adapter as UserAdapter
    users?.let {
        adapter.updateList(it)
    }
}

@BindingAdapter("api_status")
fun setProgressBar(progressBar: ProgressBar, apiStatus: ApiStatus?) {
    apiStatus?.let {
        progressBar.visibility = when(apiStatus) {
            ApiStatus.DONE -> {
                View.GONE
            }
            ApiStatus.LOADING -> {
                View.VISIBLE
            }
            ApiStatus.ERROR -> {
                View.GONE
            }
        }
    }
}