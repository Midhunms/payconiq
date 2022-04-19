package com.payconiq.app.network.model.response

data class SearchUserResponse(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: ArrayList<UserListResponseModel>
)