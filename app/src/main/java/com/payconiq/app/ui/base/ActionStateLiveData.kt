package com.payconiq.app.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import kotlin.coroutines.CoroutineContext

class ActionStateLiveData<T>(
        private val coroutineContext: CoroutineContext,
        fetchData: (suspend () -> T),
) {
    private val action = MutableLiveData<Action>()
    private var data: T? = null // backing data

    val state = action.switchMap {
        liveData(context = coroutineContext) {
            when (action.value) {
                Action.Load -> {
                    emit(UIState.Loading)
                }

                Action.SwipeRefresh -> {
                    emit(UIState.SwipeRefreshing)
                }

                Action.Retry -> {
                    emit(UIState.Retrying)
                }
            }

            try {
                val response = fetchData()
                if(response!=null){
                    data = response
                    emit(UIState.Success(response))
                }else{
                    emit(UIState.Failure(response))
                }

            } catch (exception: Exception) {
                exception.printStackTrace()
                when (action.value) {
                    Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(UIState.Success(it))
                        }
                    }
                    else -> {
                        emit(UIState.Failure(data = null))
                    }
                }
            }
        }
    }

    // Helpers for triggering different actions

    fun retry() {
        action.value = Action.Retry
    }

    fun swipeRefresh() {
        action.value = Action.SwipeRefresh
    }

    fun load() {
        action.value = Action.Load
    }
}