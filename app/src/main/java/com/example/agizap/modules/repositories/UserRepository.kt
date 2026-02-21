package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val db = Firebase.firestore
    private val path = "users"

    fun addUser(user: User) {
        db.collection(path)
            .add(user)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Adicionado a $path com ID: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Falha ao adicionar documento", e)
            }
    }
    suspend fun getUsers(): List<User> {
        return try {
            val result = Firebase.firestore.collection(path).get().await()
            result.map { doc ->
                User(
                    name = doc.getString("name") ?: "",
                    photo = doc.getString("photo") ?: "",
                    id = doc.id,
                    email = doc.getString("email") ?: ""
                )
            }
        } catch (e: Exception) {
            Log.e("Firestore", "Erro ao buscar usuários", e)
            emptyList()
        }
    }
    fun listenUsers(): Flow<List<User>> = callbackFlow {
        val listener = Firebase.firestore.collection("users")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Erro ao ouvir usuários", error)
                    trySend(emptyList())
                    return@addSnapshotListener
                }

                val users = snapshot?.documents?.map { doc ->
                    User(
                        name = doc.getString("name").orEmpty(),
                        photo = doc.getString("photo").orEmpty(),
                        id = doc.id,
                        email = doc.getString("email").orEmpty()
                    )
                } ?: emptyList()

                trySend(users)
            }

        awaitClose { listener.remove() }
    }
}