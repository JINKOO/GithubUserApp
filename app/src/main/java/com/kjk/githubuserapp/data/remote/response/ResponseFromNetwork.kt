package com.kjk.githubuserapp.data.remote.response

import com.google.gson.annotations.SerializedName

data class ResponseFromNetwork(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResult: Boolean,
    val items: List<UserResponse>
)

data class UserResponse(
    @SerializedName("login")
    val loginId: String,  // "jinkoo86"
    @SerializedName("id")
    val idNumber: Long,  // 66558109,
    @SerializedName("node_id")
    val nodeId: String, // "MDQ6VXNlcjY2NTU4MTA5",
    @SerializedName("avatar_url")
    val userProfileUrl: String, // "https://avatars.githubusercontent.com/u/66558109?v=4"
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("url")
    val url: String, //"https://api.github.com/users/jinkoo86"
    @SerializedName("html_url")
    val htmlUrl: String, // "https://github.com/jinkoo86",
    @SerializedName("followers_url")
    val followersUrl: String, //"https://api.github.com/users/jinkoo86/followers"
    @SerializedName("following_url")  // "https://api.github.com/users/jinkoo86/following{/other_user}"
    val followingUrl: String,
    @SerializedName("gists_url")
    val gistsUrl: String,  //"https://api.github.com/users/jinkoo86/gists{/gist_id}"
    @SerializedName("starred_url")
    val starredUrl: String, // "https://api.github.com/users/jinkoo86/starred{/owner}{/repo}",
    @SerializedName("subscriptions_url")
    val subscriptionsUrl: String, // "https://api.github.com/users/jinkoo86/subscriptions",
    @SerializedName("organizations_url")
    val organization: String, //: "https://api.github.com/users/jinkoo86/orgs",
    @SerializedName("repos_url")
    val repositoryUrl: String, //"https://api.github.com/users/jinkoo86/repos",
    @SerializedName("events_url")
    val eventsUrl: String,  //"https://api.github.com/users/jinkoo86/events{/privacy}",
    @SerializedName("received_events_url")
    val receivedUrl: String, //"https://api.github.com/users/jinkoo86/received_events",
    @SerializedName("type")
    val type: String,
    @SerializedName("site_admin")
    val isSiteAdmin: Boolean,
    @SerializedName("score")
    val score: Double
)