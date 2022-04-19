package com.payconiq.app.ui.activity.user.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.payconiq.app.repository.UserRepository
import com.payconiq.app.ui.activity.user.details.UserDetailsViewModel
import com.payconiq.app.ui.base.ActionStateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val repository: UserRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    companion object {
        private const val USER_NAME = "USER_NAME"
    }

    val getUserResponse = ActionStateLiveData(viewModelScope.coroutineContext + IO) {

        repository.getUserListResponse()

    }

    val getSearchUserResponse = ActionStateLiveData(viewModelScope.coroutineContext + IO) {
        val mLoginName = savedStateHandle.get<String>(USER_NAME)
            ?: throw  Exception("Parameter <${USER_NAME}> not set")
        repository.getSearchUserResponse(mLoginName)

    }

    fun processUserListRequest() {
        getUserResponse.load()
    }
    fun processSearchUserRequest(query:String) {
        savedStateHandle[USER_NAME] = query
        getSearchUserResponse.load()
    }
}
