package com.kjk.githubuserapp.ui.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
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


@BindingAdapter("empty_favorite_list_text")
fun setEmptyFavoriteListText(textView: TextView, user: List<GithubUserVO>?) {
    user?.let {
        textView.visibility = if (user.isNullOrEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}


@BindingAdapter("fill_favorite_star")
fun setFavoriteState(imageView: ImageView, isChecked: Boolean?) {
    isChecked?.let {
        Log.d(TAG, "setFavoriteState: ${it}")
        imageView.isSelected = it
    }
}

private val TAG = "BindingAdapter"