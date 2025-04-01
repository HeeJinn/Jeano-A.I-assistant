package com.example.jeano.model.repository

import com.example.jeano.model.BreiahMessageModel
import com.example.jeano.model.LeeMessageModel
import com.example.jeano.model.MessageModel
import com.example.jeano.model.dao.MessageDao
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {
    val allMessages : Flow<List<MessageModel>> = messageDao.getAllMessages()
    val allBreiahMessages : Flow<List<BreiahMessageModel>> = messageDao.getAllBreaihMessages()
    val allLeeMessages : Flow<List<LeeMessageModel>> = messageDao.getAllLeeMessages()
    suspend fun insertMessage(message: MessageModel){
        messageDao.insertMessage(message)
    }
    suspend fun  insertBreiahMessage(message: BreiahMessageModel){
        messageDao.insertBreaihMessage(message)
    }
    suspend fun  insertLeeMessage(message: LeeMessageModel){
        messageDao.insertLeeMessage(message)
    }


}