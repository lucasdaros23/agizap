package com.example.agizap.modules.model


data class Message(
    val text: String = "",
    val userId: String = "",
    val time: Long = System.currentTimeMillis(),
    val id: String = "",
    val deleted: Boolean = false,
)