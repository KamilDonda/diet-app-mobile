package com.example.dietapp.database.models

import java.io.Serializable

data class User(
    val uid: String,
    var nickname: String = ""
): Serializable {

    constructor() : this("", "")
}