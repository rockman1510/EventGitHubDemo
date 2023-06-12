package com.eventgithubdemo.design_pattern.observer

interface Observable {
    fun register(observer: Observer?)
    fun unRegister(observer: Observer?)
    fun notifyObservers()
    fun getUpdate(observer: Observer?) : Object
}