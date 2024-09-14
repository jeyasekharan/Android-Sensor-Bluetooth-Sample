package com.dreamesoft.android_sensor.utils

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
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

        fun queryPairedDevices(context: Context): Set<BluetoothDevice>?  {
            val bluetoothManager: BluetoothManager? = context.getSystemService(BluetoothManager::class.java)
            val bluetoothAdapter: BluetoothAdapter? = bluetoothManager?.adapter
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return null
            }
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            return pairedDevices
//            pairedDevices?.forEach { device ->
//                val deviceName = device.name
//                val deviceHardwareAddress = device.address // MAC address
//            }
        }
    }
}