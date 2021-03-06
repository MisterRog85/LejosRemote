package com.example.lejosremote.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.example.lejosremote.Data
import com.example.lejosremote.DataFromEV3
import com.example.lejosremote.MyBluetoothAdapter

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var data: Data

    private val _mac = MutableLiveData<String>()
    val mac: LiveData<String>
        get() = _mac

    private val _eventAdmin = MutableLiveData<Boolean>()
    val eventAdmin: LiveData<Boolean>
        get() = _eventAdmin

    private val _eventAuto = MutableLiveData<Boolean>()
    val eventAuto: LiveData<Boolean>
        get() = _eventAuto

    private val _dataInterface = MutableLiveData<ByteArray>()
    val dataInterface: LiveData<ByteArray>
        get() = _dataInterface

    init {

        data = Data(application.applicationContext)
        _mac.value = data.getMac()

        _eventAdmin.value = false
        _eventAuto.value = false

        updateUIWithData()
    }

    fun onUp() {
        MyBluetoothAdapter.sendMsg(1)
    }

    fun onRight() {
        MyBluetoothAdapter.sendMsg(2)
    }

    fun onLeft() {
        MyBluetoothAdapter.sendMsg(3)
    }

    fun onDown() {
        MyBluetoothAdapter.sendMsg(4)
    }

    fun onAuto() {
        MyBluetoothAdapter.sendMsg(5)
        _eventAuto.value = true
    }

    fun goAdmin() {
        _eventAdmin.value = true
    }

    fun onOff() {
        MyBluetoothAdapter.sendMsg(6)
    }

    fun klaxon() {
        MyBluetoothAdapter.sendMsg(7)
    }

    fun onPlus() {
        MyBluetoothAdapter.sendMsg(8)
    }

    fun onMoins() {
        MyBluetoothAdapter.sendMsg(9)
    }

    fun updateUIWithData() {
        DataFromEV3.dataFromEV3.observeForever {elements ->
            if (elements != null) {
                _dataInterface.postValue(DataFromEV3.dataFromEV3.value)
            }
        }
    }
}
