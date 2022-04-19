package com.payconiq.app.repository

import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel

interface UserRepository {

    suspend fun getUserListResponse(): ArrayList<UserListResponseModel>
    suspend fun getUserDetailsResponse(loginName:String): UserListResponseModel
    suspend fun getSearchUserResponse(query:String): SearchUserResponse
}