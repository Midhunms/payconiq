package com.payconiq.app.network.model.response


data class UserListResponseModel(
    val login: String? = "",
    val node_id: String? = "",
    val name: String? = "",
    val company: String? = "",
    val blog: String? = "",
    val location: String? = "",
    val twitter_username: String? = "",
    val avatar_url: String? = "",
    val gravatar_id: String? = "",
    val url: String? = "",
    val html_url: String? = "",
    val followers_url: String? = "",
    val following_url: String? = "",
    val gists_url: String? = "",
    val starred_url: String? = "",
    val subscriptions_url: String? = "",
    val type: String? = "",
    val id: Int = -1,

)