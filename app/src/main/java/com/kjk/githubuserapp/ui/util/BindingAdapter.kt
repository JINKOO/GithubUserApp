package com.kjk.githubuserapp.ui.util

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kjk.githubuserapp.R
import com.kjk.githubuserapp.domain.GithubUserVO
import com.kjk.githubuserapp.ui.adapter.GithubUserAdapter
import com.kjk.githubuserapp.ui.localsearch.LoadType
import com.kjk.githubuserapp.ui.remotesearch.ApiStatus

@BindingAdapter("user_list")
fun setUserList(recyclerView: RecyclerView, githubUsers: List<GithubUserVO>?) {
    val adapter = recyclerView.adapter as GithubUserAdapter
    githubUsers?.let {
        adapter.addHeaderAndSubmitList(it)
    }
}


@BindingAdapter("favorite_user_list", "searched_user_list", "load_type")
fun setFavoriteUserLists(
    recyclerView: RecyclerView,
    favoriteUsers: List<GithubUserVO>?,
    searchedFavoriteUsers: List<GithubUserVO>?,
    loadType: LoadType?
) {
    val adapter = recyclerView.adapter as GithubUserAdapter
    loadType?.let {
        when (loadType) {
            LoadType.LOAD_ALL -> {
                favoriteUsers?.let {
                    adapter.addHeaderAndSubmitList(it)
                }
            }
            LoadType.LOAD_SEARCHED_RESULT -> {
                searchedFavoriteUsers?.let {
                    adapter.addHeaderAndSubmitList(it)
                }
            }
        }
    }
}

@BindingAdapter("api_status")
fun setProgressBar(progressBar: ProgressBar, apiStatus: ApiStatus?) {
    apiStatus?.let {
        progressBar.visibility = when (apiStatus) {
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


@BindingAdapter("no_result_visibility")
fun setNoResultTextVisibility(textView: TextView, loadType: LoadType?) {
    loadType?.let {
        textView.apply {
            visibility = when (loadType) {
                LoadType.LOAD_ALL -> {
                    View.GONE
                }
                LoadType.LOAD_SEARCHED_RESULT -> {
                    View.VISIBLE
                }
            }
        }
    }
}


@BindingAdapter("fill_favorite_star")
fun setFavoriteState(imageView: ImageView, isChecked: Boolean?) {
    isChecked?.let {
        imageView.isSelected = it
    }
}


@BindingAdapter("empty_result_visibility")
fun setEmptySearchResultVisibility(textView: TextView, searchedUsers: List<GithubUserVO>?) {
    searchedUsers?.let {
        textView.apply {
            visibility = if (it.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }
}

@BindingAdapter("index_header")
fun setIndexHeader(textView: TextView, name: String?) {
    name?.let {
        textView.apply {
            text = name.first().toString()
        }
    }
}

private val TAG = "BindingAdapter"