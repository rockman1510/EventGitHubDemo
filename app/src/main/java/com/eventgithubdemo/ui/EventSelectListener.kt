package com.eventgithubdemo.ui

import com.eventgithubdemo.api.model.Event

interface EventSelectListener {
    fun onEventSelected(event: Event)
}