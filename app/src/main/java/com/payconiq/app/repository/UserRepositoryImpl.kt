package com.payconiq.app.repository

import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel
import com.payconiq.app.network.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dataSource: UserDataSource,
) : UserRepository {
    override suspend fun getUserListResponse(): ArrayList<UserListResponseModel> {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.requestGitUserList()
        }
    }

    override suspend fun getUserDetailsResponse(loginName:String): UserListResponseModel {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getUserDetailsResponse(loginName)
        }
    }

    override suspend fun getSearchUserResponse(query: String): SearchUserResponse {
        return withContext(Dispatchers.IO) {
            return@withContext dataSource.getSearchUserResponse(query)
        }
    }

}