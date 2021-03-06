package com.example.lejosremote.admin

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lejosremote.Data
import com.example.lejosremote.MyBluetoothAdapter

class AdminFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private var data: Data

    private val _mac = MutableLiveData<String>()
    val mac: LiveData<String>
        get() = _mac

    private val _mdp = MutableLiveData<String>()
    val mdp: LiveData<String>
        get() = _mdp

    init {
        data = Data(application.applicationContext)
        _mac.value = data.getMac()
        _mdp.value = data.getMdp()

    }

    fun onLog() {
        MyBluetoothAdapter.sendMsg(10)
    }

    fun onShutdown() {
        MyBluetoothAdapter.sendMsg(99)
    }

    fun onUpdateMac(mac: String) {
        data.setMac(mac)
    }

    fun onUpdatePassword(mdp: String) {
        data.setMdp(mdp)
    }
}
