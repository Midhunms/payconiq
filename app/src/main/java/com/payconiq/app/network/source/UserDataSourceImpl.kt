package com.payconiq.app.network.source

import com.payconiq.app.network.ApiService
import com.payconiq.app.network.model.response.SearchUserResponse
import com.payconiq.app.network.model.response.UserListResponseModel
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    UserDataSource {

    override suspend fun requestGitUserList():ArrayList<UserListResponseModel> {
        return try {
            apiService
                .requestUserListAsync()
                .await()
        } catch (e: Exception) {
            return ArrayList()
        } catch (e: Exception) {
            return ArrayList()
        }
    }

    override suspend fun getUserDetailsResponse(loginName:String): UserListResponseModel {
        return try {
            apiService
                .requestUserDetailsAsync(loginName)
                .await()
        } catch (e: Exception) {
            return UserListResponseModel()
        } catch (e: Exception) {
            return UserListResponseModel()
        }
    }

    override suspend fun getSearchUserResponse(query: String): SearchUserResponse {
        return try {
            apiService
                .requestSearchUsersAsync(query)
                .await()
        } catch (e: Exception) {
            return SearchUserResponse(0,false, ArrayList())
        } catch (e: Exception) {
            return SearchUserResponse(0,false,ArrayList())
        }
    }
}