package com.eventgithubdemo

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var phoneCallStateReceiver : PhoneCallStateReceiver

    lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        phoneCallStateReceiver = PhoneCallStateReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(phoneCallStateReceiver, intentFilter)
        countDownTimer = object : CountDownTimer(10000, 1000){
            override fun onTick(p0: Long) {
                Log.d("HuyLV", "PhoneCallState: ${phoneCallStateReceiver.phoneCallState}")
            }

            override fun onFinish() {
                start()
            }
        }.start()
    }

    override fun onStop() {
        super.onStop()
        if(countDownTimer != null)
            countDownTimer.cancel()
        if(phoneCallStateReceiver != null)
            unregisterReceiver(phoneCallStateReceiver)
    }
}