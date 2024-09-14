package com.dreamesoft.android_sensor.presentation.Bluetooth.Bluetooth.views

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.dreamesoft.android_sensor.presentation.BoundService.BoundServiceActivity
import com.dreamesoft.android_sensor.utils.BluetoothUtils


class BluetoothActivity: ComponentActivity() {

    var bluetoothdevices = mutableStateListOf<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBluetooth()
        BluetoothUtils.queryPairedDevices(this)?.let { bluetoothdevices.addAll(it.toList()) }
        setContent {
            BluetoothDetection()
        }
    }

    private fun checkBluetooth() {
        if(BluetoothUtils.openBluetooth(this)) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            ActivityCompat.startActivityForResult(this, enableBtIntent, 100, null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            100 -> {
                if(resultCode == RESULT_OK) {
                    Toast.makeText(this, "bluetooth activated", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun goToBoundService() {
        val intent = Intent(this@BluetoothActivity, BoundServiceActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    @Composable
    fun BluetoothDetection() {
        Column {
            Button(onClick = { goToBoundService() }) {
                Text(text= "Go to bound service")
            }


            Button(onClick = { checkBluetooth() }) {
                Text(text= "Check bluetooth")
            }

            LazyColumn {
                itemsIndexed(bluetoothdevices) { index, item ->
                    Row {
                        Text(text= item?.name ?: "")
                        Text(text= item?.address ?: "")
                    }
                }
            }
        }
    }
}


