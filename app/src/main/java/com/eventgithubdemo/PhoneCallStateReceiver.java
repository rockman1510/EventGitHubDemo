package com.eventgithubdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

public class PhoneCallStateReceiver extends BroadcastReceiver {

    private PhoneCallState mPhoneCallState;
    public PhoneCallStateReceiver(){
        mPhoneCallState = PhoneCallState.CALL_IDLE;
    }

    public PhoneCallState getPhoneCallState(){
        return mPhoneCallState;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d("HuyLV", "onReceive: " + state);
            if(TelephonyManager.EXTRA_STATE_RINGING.equals(state)){
                mPhoneCallState = PhoneCallState.INCOMING_CALL;
            } else if(TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)){
                mPhoneCallState = PhoneCallState.CALL_RECEIVED;
            } else if(TelephonyManager.EXTRA_STATE_IDLE.equals(state)){
                mPhoneCallState = PhoneCallState.CALL_IDLE;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
