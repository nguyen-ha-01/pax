package com.code.pixabay.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class LoginVm():ViewModel() {
    val savedStateHandle = SavedStateHandle()
    fun store(key: String ,  data: Any){
        savedStateHandle.remove<String>(key)
        savedStateHandle.set(key,data)
    }
    fun get(key: String):Any?=savedStateHandle.get(key)
    fun check(key : String):Boolean = if (savedStateHandle.get<Any>(key) != null) {
        true
    }else false
}