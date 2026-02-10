package com.example.agizap.modules.repositories

import android.util.Log
import com.example.agizap.model.User
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
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
}