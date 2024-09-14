package com.dreamesoft.android_sensor.presentation.BoundService

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dreamesoft.android_sensor.utils.MessageHandler
import com.dreamesoft.android_sensor.utils.PlayerService


class BoundServiceActivity: ComponentActivity() {

    private lateinit var playerService: PlayerService
    private var mBound: Boolean = false
    var getPrintNumber by mutableStateOf("")

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as PlayerService.LocalBinder
            playerService = binder.getService()
            playerService.messageHandler = messageHandler
            mBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ServiceUI()
        }
    }


    @Composable
    fun ServiceUI(modifier: Modifier = Modifier) {
        var randomNumber by remember { mutableStateOf("") }
        Column {
            Button(modifier = Modifier
                .wrapContentWidth()
                .height(80.dp), onClick = {
                val intent = Intent(this@BoundServiceActivity, PlayerService::class.java)
                startService(intent)
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            }) {
                Text(text = "Start service")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(modifier = Modifier
                .wrapContentWidth()
                .height(80.dp), onClick = {
                val intent = Intent(this@BoundServiceActivity,
                    BoundServiceActivity::class.java)
                stopService(intent)
            }) {
                Text(text = "Stop service")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Get random number - $randomNumber", modifier = Modifier.clickable {
                randomNumber = playerService.getRandomNumber.toString()
            })

            Text(text = "Get Timer number - $getPrintNumber", modifier = Modifier.clickable {
                getPrintNumber = playerService.getPrintNumber.toString()
            })
        }
    }


    val messageHandler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what) {
                1 ->
                {
                    getPrintNumber = playerService.getPrintNumber.toString()
                }
            }
        }
    }



}