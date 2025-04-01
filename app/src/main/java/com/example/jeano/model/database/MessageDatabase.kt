package com.example.jeano.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jeano.model.BreiahMessageModel
import com.example.jeano.model.LeeMessageModel
import com.example.jeano.model.MessageModel
import com.example.jeano.model.dao.MessageDao
import kotlin.contracts.Returns

@Database(entities = [MessageModel::class, BreiahMessageModel::class, LeeMessageModel::class], version = 1, exportSchema = false)
abstract class MessageDatabase: RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object{
        @Volatile
        var INSTANCE: MessageDatabase? = null
        fun getDatabase(context: Context): MessageDatabase{
            return INSTANCE ?:synchronized(this) {
                val instance =Room.databaseBuilder(
                    context,
                    MessageDatabase::class.java,
                    "message_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
