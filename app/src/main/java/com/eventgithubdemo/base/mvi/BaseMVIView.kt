package com.eventgithubdemo.base.mvi

interface BaseMVIView<STATE> {
    fun onCallBackState(state: STATE)
}