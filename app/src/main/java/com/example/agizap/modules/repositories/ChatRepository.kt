package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ChatRepository {
    private val db = Firebase.firestore
    private val path = "chats"

    constructor()

    fun addChat(chat: Chat) {
        db.collection(path)
            .add(chat)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Adicionado a $path com ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Falha ao adicionar documento", e)
            }
    }

    suspend fun getChats(): List<Chat> {
        return try {
            val result = Firebase.firestore.collection(path).get().await()
            result.map { chatDoc ->
                val messagesSnapshot = chatDoc.reference.collection("messages").get().await()
                val messages = messagesSnapshot.map{ messageDoc ->
                    Message(
                        text = messageDoc.getString("text") ?: "",
                        userId = messageDoc.getString("userId") ?: "",
                        time = messageDoc.getLong("time") ?: 0,
                        id = messageDoc.id
                    )
                }
                Chat(
                    name = chatDoc.getString("name") ?: "",
                    messages = messages.sortedBy { it.time },
                    users = chatDoc.get("users") as List<String> ?: emptyList(),
                    id = chatDoc.id,
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar conversas", e)
            emptyList()
        }
    }
}