package com.dreamesoft.android_sensor.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat.getSystemService

class BluetoothUtils {
    companion object {
        fun openBluetooth(context: Context): Boolean {
            val bluetoothManager: BluetoothManager? =
                context.getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter = bluetoothManager?.adapter
                ?: // Device doesn't support Bluetooth
                return false

            return if (bluetoothAdapter?.isEnabled == false) {
                false
            } else {
                true
            }
        }

    }


}