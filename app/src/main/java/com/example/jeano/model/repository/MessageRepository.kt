package com.example.jeano.model.repository

import com.example.jeano.model.MessageModel
import com.example.jeano.model.dao.MessageDao
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {
    val allMessages : Flow<List<MessageModel>> = messageDao.getAllMessages()
    suspend fun insertMessage(message: MessageModel){
        messageDao.insertMessage(message)
    }


}