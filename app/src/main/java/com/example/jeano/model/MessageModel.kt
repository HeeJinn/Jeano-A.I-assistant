package com.example.jeano.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "message") val message : String,
    @ColumnInfo(name = "role") val role : String
)