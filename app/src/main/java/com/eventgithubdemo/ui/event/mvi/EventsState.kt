package com.eventgithubdemo.ui.event.mvi

import com.eventgithubdemo.api.model.Event

sealed class EventsState {
    object OnLoading : EventsState()
    class OnSuccess(val dataList: ArrayList<Event>) : EventsState()
    class OnError(val message: String) : EventsState()
}