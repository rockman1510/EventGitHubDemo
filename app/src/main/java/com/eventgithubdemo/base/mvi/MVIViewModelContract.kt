package com.eventgithubdemo.base.mvi

interface MVIViewModelContract<INTENT> {
    fun processIntent(intent: INTENT)
}