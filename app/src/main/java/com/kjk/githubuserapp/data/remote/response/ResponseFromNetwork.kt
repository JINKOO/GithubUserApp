package com.kjk.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 *  remote nework로 부터 받은 응답 class
 */

data class ResponseFromNetwork(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResult: Boolean,
    val items: List<UserResponse>
)

data class UserResponse(
    @SerializedName("login")
    val loginId: String,
    @SerializedName("id")
    val idNumber: Long,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("avatar_url")
    val userProfileUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,
    @SerializedName("starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String,
    @SerializedName("organizations_url")
    val organization: String,
    @SerializedName("repos_url")
    val repositoryUrl: String,
    @SerializedName("events_url")
    val eventsUrl: String,
    @SerializedName("received_events_url")
    val receivedUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("site_admin")
    val isSiteAdmin: Boolean,
    @SerializedName("score")
    val score: Double
)