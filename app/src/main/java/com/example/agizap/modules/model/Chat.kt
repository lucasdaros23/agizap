package com.example.agizap.model

data class Chat(
    val name: String = "Grupo",
    val messages: List<Message> = emptyList(),
    val users: List<String> = emptyList(),
    val id: String = "",
    val photo: String = "https://www.creativefabrica.com/wp-content/uploads/2019/02/Group-Icon-by-Kanggraphic-580x386.jpg"
)
