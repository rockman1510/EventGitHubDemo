package com.eventgithubdemo.design_pattern.observer

interface Observer {
    fun update()
    fun setObservable(observable: Observable?)
}