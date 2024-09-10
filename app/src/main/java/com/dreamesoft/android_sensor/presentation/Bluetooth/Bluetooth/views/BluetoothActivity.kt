package com.dreamesoft.android_sensor.presentation.Bluetooth.Bluetooth.views

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.core.app.ActivityCompat
import com.dreamesoft.android_sensor.utils.BluetoothUtils


class BluetoothActivity: ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkBluetooth()
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

    @Composable
    fun BluetoothDetection(modifier: Modifier = Modifier) {
        Column {
            Button(onClick = { checkBluetooth() }) {
                Text(text= "Check bluetooth")
            }
        }
    }
}


