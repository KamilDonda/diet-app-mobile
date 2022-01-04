package com.example.dietapp.services

import android.content.Context
import com.example.dietapp.database.room.Db

class DatabaseService(context: Context) {
    val db = Db.create(context)
}