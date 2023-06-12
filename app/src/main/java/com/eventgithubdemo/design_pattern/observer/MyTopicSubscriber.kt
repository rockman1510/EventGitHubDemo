package com.eventgithubdemo.design_pattern.observer

import android.util.Log

class MyTopicSubscriber(private val name: String) : Observer {
    private var topic: Observable? = null
    override fun update() {
        val message = topic?.getUpdate(this) as String
        if (message.isNullOrEmpty()) {
            Log.d("HuyLV", "$name.update(): No new message")
        } else {
            Log.d("HuyLV", "$name.update(): $message")
        }
    }

    override fun setObservable(observable: Observable?) {
        observable?.let { topic = it }
    }
}