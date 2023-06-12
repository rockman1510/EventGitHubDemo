package com.eventgithubdemo.design_pattern.proxy

import android.util.Log

class CommandExecutorImpl : CommandExecutor {
    override fun runCommand(cmd: String) {
        Log.d("HuyLV", "'$cmd' command executed.")
    }
}