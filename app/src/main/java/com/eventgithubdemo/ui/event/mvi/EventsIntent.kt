package com.eventgithubdemo.ui.event.mvi

sealed class EventsIntent {
    class FetchData(val isPeriodic: Boolean = true, val perPage: Int = 5) : EventsIntent()
    object SaveSuccessState : EventsIntent()
}
