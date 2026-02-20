package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.example.agizap.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessageRepository {
    private val db = Firebase.firestore

    fun addMessage(chatId: String, message: Message) {
        if (chatId.isBlank()) {
            Log.e("Firestore", "Erro: chatId vazio — não é possível salvar a mensagem")
            return
        }

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Mensagem adicionada ao chat $chatId com ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Falha ao adicionar mensagem", e)
            }
    }

    suspend fun getMessages(chatId: String): List<Message> {
        return try {
            val result = db.collection("chats")
                .document(chatId)
                .collection("messages")
                .orderBy("time")
                .get()
                .await()

            result.map { doc ->
                Message(
                    text = doc.getString("text") ?: "",
                    userId = doc.getString("userId") ?: "",
                    time = doc.getLong("time") ?: 0L,
                    id = doc.id
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar mensagens", e)
            emptyList()
        }
    }

    fun listenMessages(chatId: String): Flow<List<Message>> = callbackFlow {
        if (chatId.isBlank()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("time")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Erro ao ouvir mensagens", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val messages = snapshot?.documents?.map { doc ->
                    Message(
                        text = doc.getString("text").orEmpty(),
                        userId = doc.getString("userId").orEmpty(),
                        time = doc.getLong("time") ?: 0L,
                        id = doc.id
                    )
                }?.sortedBy { it.time } ?: emptyList()

                trySend(messages)
            }

        awaitClose { listener.remove() }
    }
}