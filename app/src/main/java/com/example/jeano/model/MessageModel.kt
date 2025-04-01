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

@Entity(tableName = "breaih_messages")
data class BreiahMessageModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "breiah_message") val message : String,
    @ColumnInfo(name = "breiah_role") val role : String
)

@Entity(tableName = "lee_messages")
data class LeeMessageModel(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "lee_message") val message : String,
    @ColumnInfo(name = "lee_role") val role : String
)