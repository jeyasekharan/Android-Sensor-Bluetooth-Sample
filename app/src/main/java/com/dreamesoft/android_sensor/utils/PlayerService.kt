package com.dreamesoft.android_sensor.utils

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.util.Log
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class PlayerService: Service() {

    val localBinder = LocalBinder()
    lateinit var messageHandler: Handler

    val getRandomNumber
        get() = Random(1000).nextInt(1000)

    var getPrintNumber: Int = 0

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return localBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timer().schedule(object: TimerTask() {
            override fun run() {
                getPrintNumber += 1
                Log.e("the value of i ", "$getPrintNumber")
                val msg = messageHandler.obtainMessage(1, "$getPrintNumber")
                msg.sendToTarget()
            }
        },1000,1000)
        return START_STICKY
    }

    inner class LocalBinder: Binder() {
        fun getService(): PlayerService = this@PlayerService
    }


}
