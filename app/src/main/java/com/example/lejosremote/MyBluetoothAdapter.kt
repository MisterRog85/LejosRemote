package com.example.lejosremote

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.DataInputStream
import java.io.InputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.util.*

object MyBluetoothAdapter: Application() {

    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private var bAdapter: BluetoothAdapter
    private lateinit var socket: BluetoothSocket
    private lateinit var device: BluetoothDevice
    private lateinit var output: OutputStreamWriter
    private lateinit var input: DataInputStream

    lateinit var contextGlobal: Context

    lateinit var data: Data

    init {
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bAdapter == null) {
            Log.i("Init bAdapter", "adapter non dispo")
        } else {
            if(!bAdapter.isEnabled)
                bAdapter.enable()
                Log.i("Init bAdapter", "adapter démarré")
        }
    }

    fun setGlobalContext(ctx: Context) {
        contextGlobal = ctx
        data = Data(contextGlobal)
    }

    fun connect() {
        device = bAdapter.getRemoteDevice(data.getMac())

        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
            socket.connect()
            _isConnected.value = true
            Log.i("Connect bAdapter", "connexion à la brique réussi")
        } catch (e: Exception) {
            Log.i("Connect bAdapter", "erreur de connexion à la brique")
        }
    }

    fun disconnect() {
        output.close()
        socket.close()
        Log.i("Disonnect bAdapter", "déconnexion réussi")
    }

    fun sendMsg(msg: Int) {
        try {
            output = OutputStreamWriter(socket.outputStream)
            output.write(msg)
            output.flush()
        } catch (e: Exception) {
            Log.i("Send msg", "erreur envoie du message")
        }
    }

    fun readMsg(): ByteArray {
        val buffer: ByteArray = ByteArray(8)

        input = DataInputStream(socket.inputStream)
        input.read(buffer, 0, 8)

        Log.i("MyBluetoothAdapter", "message reçu vitesse : " + buffer[2])

        return buffer
    }
}