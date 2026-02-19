package com.example.agizap.modules.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.agizap.model.User
import com.google.gson.Gson

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveUser(user: User) {
        val json = gson.toJson(user)
        prefs.edit()
            .putString("user", json)
            .putBoolean("logged_in", true)
            .apply()
    }

    fun getUser(): User? {
        val json = prefs.getString("user", null) ?: return null
        return gson.fromJson(json, User::class.java)
    }

    fun saveUserData(uid: String, email: String) {
        prefs.edit()
            .putString("uid", uid)
            .putString("email", email)
            .putBoolean("logged_in", true)
            .apply()
    }

    fun getUid(): String? = prefs.getString("uid", null)
    fun getEmail(): String? = prefs.getString("email", null)
    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
