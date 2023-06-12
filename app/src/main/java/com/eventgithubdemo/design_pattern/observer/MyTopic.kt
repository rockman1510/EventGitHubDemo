package com.eventgithubdemo.design_pattern.observer

import android.util.Log

class MyTopic : Observable {
    private var observers = arrayListOf<Observer>()
    private var message = ""
    private var changed = false
    private val MUTEX = Object()

    override fun register(observer: Observer?) {
        observer?.let {
            synchronized(MUTEX) {
                if (!observers.contains(it)) {
                    observers.add(it)
                }
            }
        }
    }

    override fun unRegister(observer: Observer?) {
        synchronized(MUTEX){
            observers.remove(observer)
        }
    }

    override fun notifyObservers() {
        var localObservers = arrayListOf<Observer>()
        synchronized(MUTEX){
            if(changed){
             localObservers = observers
             changed = false
            }
        }
        localObservers.forEach {
            it.update()
        }
    }

    override fun getUpdate(observer: Observer?): Object {
        return message as Object
    }

    fun postMessage(message: String){
        Log.d("HuyLV", "postMessage: $message")
        this.message = message
        changed = true
        notifyObservers()
    }
}