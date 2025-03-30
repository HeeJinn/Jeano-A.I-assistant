package com.example.jeano.viewmodel


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jeano.model.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

const val API_KEY = "AIzaSyAqRf0UBDJbabBf2AhooL6YHN7OI4k2AoU"

class JeanoChatViewModel : ViewModel() {

    val messageList = mutableStateListOf<MessageModel>()
    val queryContext = "No matter what happens always Reply in makatang tagalog that is like an ancient filipino courting"

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = API_KEY,
    )


    fun sendQuestion(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){
                            text(it.message)
                        }
                    }
                )
                messageList.add(MessageModel(question.trim(), "user"))
                messageList.add(MessageModel("Typing...", "model"))
                val response = chat.sendMessage("context is: $queryContext, question is: $question")
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(response.text.toString().trim(), "model"))
            }catch (e: Exception){
                messageList.removeAt(messageList.lastIndex)
                messageList.add(MessageModel(e.message.toString(), "model"))

            }


        }
    }
}