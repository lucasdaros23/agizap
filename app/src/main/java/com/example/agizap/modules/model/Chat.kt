package com.example.agizap.model

data class Chat(
    val name: String = "",
    val messages: List<Message> = emptyList(),
    val users: List<String> = emptyList(),
    val id: String = "",
    val photo: String = ""
)
