package com.example.jeano.model

import androidx.compose.ui.graphics.painter.Painter


data class DevelopersData(
    val profile: Painter,
    val name: String,
    val role: String,
    val description: String
)