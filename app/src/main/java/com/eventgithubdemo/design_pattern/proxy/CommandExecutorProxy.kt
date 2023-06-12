package com.eventgithubdemo.design_pattern.proxy

import android.util.Log

class CommandExecutorProxy() : CommandExecutor {
    private var isAdmin = false
    private lateinit var executor: CommandExecutor

    constructor(user: String, password: String) : this() {
        if ("HuyLV".equals(user) && "levanhuy".equals(password))
            isAdmin = true
        executor = CommandExecutorImpl()
    }

    override fun runCommand(cmd: String) {
        if (isAdmin) {
            executor.runCommand(cmd)
        } else {
            if (cmd.trim().startsWith("rm")) {
                Log.d("HuyLV", "rm command is not allowed for non-admin user!")
            } else {
                executor.runCommand(cmd)
            }
        }
    }
}