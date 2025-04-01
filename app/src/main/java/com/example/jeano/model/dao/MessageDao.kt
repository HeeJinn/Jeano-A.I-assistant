package com.example.jeano.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jeano.model.BreiahMessageModel
import com.example.jeano.model.LeeMessageModel
import com.example.jeano.model.MessageModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages ORDER BY id ASC")
    fun getAllMessages(): Flow<List<MessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(vararg message: MessageModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreaihMessage(vararg message: BreiahMessageModel)

    @Query("SELECT * FROM breaih_messages ORDER BY id ASC")
    fun getAllBreaihMessages(): Flow<List<BreiahMessageModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeeMessage(vararg message: LeeMessageModel)

    @Query("SELECT * FROM lee_messages ORDER BY id ASC")
    fun getAllLeeMessages(): Flow<List<LeeMessageModel>>
}