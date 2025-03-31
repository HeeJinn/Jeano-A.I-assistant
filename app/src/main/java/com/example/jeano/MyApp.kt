package com.example.jeano

import android.app.Application
import com.example.jeano.model.database.MessageDatabase
import com.example.jeano.model.repository.MessageRepository

class MyApp: Application() {
    val database by lazy { MessageDatabase.getDatabase(this) }
    val repository by lazy { MessageRepository(database.messageDao()) }


}