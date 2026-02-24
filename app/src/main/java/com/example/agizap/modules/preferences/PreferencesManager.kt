package com.example.agizap.modules.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.agizap.model.User
import com.google.gson.Gson

class PreferencesManager(private val prefs: SharedPreferences) {

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

    fun saveTheme(value: String){
        prefs.edit().putString("theme", value).apply()
    }

    fun getTheme() = prefs.getString("theme", null)
    fun isLoggedIn(): Boolean = prefs.getBoolean("logged_in", false)

    fun clear() {
        prefs.edit().remove("logged_in").remove("user").apply()
    }
}
