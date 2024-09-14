package com.dreamesoft.android_sensor.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message


open class MessageHandler(context: Context): Handler(Looper.getMainLooper()) {

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        when(msg.what) {

        }
    }

}