package com.payconiq.app.ui.base

sealed class Action {
    object Load : Action()
    object SwipeRefresh : Action()
    object Retry : Action()
}