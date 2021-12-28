package com.example.dietapp.services

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable

data class User(
    val uid: String,
    var nickname: String = ""
): Serializable {

    constructor() : this("", "")
}