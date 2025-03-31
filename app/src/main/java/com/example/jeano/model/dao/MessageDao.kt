package com.example.jeano.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jeano.model.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<MessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(vararg message: MessageModel)

}