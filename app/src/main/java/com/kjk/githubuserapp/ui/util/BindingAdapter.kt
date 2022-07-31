package com.kjk.githubuserapp.ui.util

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.ui.adapter.UserAdapter
import com.kjk.githubuserapp.ui.remotesearch.ApiStatus

@BindingAdapter("user_list")
fun setUserList(recyclerView: RecyclerView, githubUsers: List<GithubUserVO>?) {
    val adapter = recyclerView.adapter as UserAdapter
    githubUsers?.let {
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

@BindingAdapter("user_profile_image")
fun setUserProfileImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        Glide.with(imageView.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
            )
            .centerCrop()
            .circleCrop()
            .into(imageView)
    }
}