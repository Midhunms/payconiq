package com.payconiq.app.network.source

import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel

interface UserDataSource {

    suspend fun requestGitUserList(): ArrayList<UserListResponseModel>
    suspend fun getUserDetailsResponse(loginName:String): UserListResponseModel
    suspend fun getSearchUserResponse(query:String): SearchUserResponse


}