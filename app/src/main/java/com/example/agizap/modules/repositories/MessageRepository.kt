package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class MessageRepository {
    private val db = Firebase.firestore

    fun addMessage(message: Message, chatId: String) {
        db.collection("chats/${chatId}/messages")
            .add(message)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Adicionado a messages com ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Falha ao adicionar documento", e)
            }
    }

    suspend fun getMessages(chatId: String): List<Message> {
        return try {
            val result = Firebase.firestore.collection("chats/${chatId}/messages").get().await()
            result.map { doc ->
                Message(
                    text = doc.getString("text") ?: "",
                    userId = doc.getString("userId") ?: "",
                    time = doc.getLong("time") ?: 0,
                    id = doc.id
                )

            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar mensagens", e)
            emptyList()
        }
    }
}