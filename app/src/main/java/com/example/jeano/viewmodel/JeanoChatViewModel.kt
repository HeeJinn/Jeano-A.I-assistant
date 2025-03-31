package com.example.jeano.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jeano.model.MessageModel
import com.example.jeano.model.repository.MessageRepository // Ensure correct repository import
import com.google.ai.client.generativeai.Chat // Import Chat if you want persistent chat object
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.Content // Import Content
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
// import kotlinx.coroutines.flow.WhileSubscribed // Not needed directly for SharingStarted.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


const val API_KEY = "AIzaSyAqRf0UBDJbabBf2AhooL6YHN7OI4k2AoU"

class JeanoChatViewModel(private val messageRepository: MessageRepository) : ViewModel() {

    val allMessages: StateFlow<List<MessageModel>> = messageRepository.allMessages
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    val queryContext = "You are a Professional Teacher, always reply in manners"

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY,
    )

    private fun insertMessage(message: MessageModel) {
        viewModelScope.launch(Dispatchers.IO) {
            messageRepository.insertMessage(message)
        }
    }

    fun sendQuestion(question: String) {
        val userMessageContent = question.trim()
        insertMessage(MessageModel(0, userMessageContent, "user"))

        _isTyping.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentHistory = allMessages.value.map {
                    content(it.role) { text(it.message) }
                }
                val chat = generativeModel.startChat(history = currentHistory)

                val response = chat.sendMessage("context is: $queryContext, question is: $userMessageContent")
                _isTyping.value = false

                val modelResponseText = response.text?.trim()

                if (!modelResponseText.isNullOrBlank()) {
                    insertMessage(MessageModel(0, modelResponseText, "model"))
                }

            } catch (e: Exception) {
                _isTyping.value = false
                Log.e("ViewModelAI", "API Error: ${e.message}", e)
                insertMessage(MessageModel(0, "Error: ${e.message}", "model"))
            }
        }
    }
}


class JeanoChatViewModelFactory(private val messageRepository: MessageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JeanoChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JeanoChatViewModel(messageRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}