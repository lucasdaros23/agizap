package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.Chat
import com.example.agizap.model.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ChatRepository {
    private val db = Firebase.firestore
    private val path = "chats"

    fun addChat(chat: Chat): String {
        val ref = db.collection(path).document()
        val chatId = ref.id

        val chatData = hashMapOf(
            "id" to chatId,
            "users" to chat.users,
            "name" to chat.name,
            "photo" to chat.photo
        )

        ref.set(chatData)
            .addOnSuccessListener {
                Log.d("Firestore", "Chat adicionado com ID: $chatId")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Falha ao adicionar chat", e)
            }

        return chatId
    }

    suspend fun getChats(): List<Chat> {
        return try {
            val snapshot = db.collection(path).get().await()
            snapshot.map { doc ->
                val chatId = doc.id
                val messagesSnapshot = doc.reference
                    .collection("messages")
                    .orderBy("time")
                    .get()
                    .await()

                val messages = messagesSnapshot.map { messageDoc ->
                    Message(
                        text = messageDoc.getString("text") ?: "",
                        userId = messageDoc.getString("userId") ?: "",
                        time = messageDoc.getLong("time") ?: 0L,
                        id = messageDoc.id
                    )
                }

                Chat(
                    id = chatId,
                    name = doc.getString("name") ?: "",
                    photo = doc.getString("photo") ?: "",
                    users = doc.get("users") as? List<String> ?: emptyList(),
                    messages = messages
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar chats", e)
            emptyList()
        }
    }
    fun listenChats(): Flow<List<Chat>> = callbackFlow {
        val listener = Firebase.firestore.collection("chats")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Erro ao ouvir chats", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val chats = snapshot?.documents?.map { doc ->
                    Chat(
                        id = doc.id,
                        name = doc.getString("name").orEmpty(),
                        photo = doc.getString("photo").orEmpty(),
                        users = doc.get("users") as? List<String> ?: emptyList(),
                        messages = emptyList()
                    )
                } ?: emptyList()

                trySend(chats)
            }

        awaitClose { listener.remove() }
    }
}