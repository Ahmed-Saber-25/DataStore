package com.ahmedsaber.protodatastore.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ahmedsaber.protodatastore.repo.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application):AndroidViewModel(application) {
    private val repository = DataStoreRepository(application)
    val readFromProtoDataStore = repository.readProto.asLiveData()
    val readFromPrefDataStore = repository.readFromPrefDataStore.asLiveData()
    fun updateValueUsingProto(firstName:String, lastName:String, age:Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToProtoDataStore(firstName,lastName,age)
    }
    fun updateValueUsingPref(firstName:String, lastName:String, age:Int) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToPrefDataStore(firstName,lastName,age)
    }
}