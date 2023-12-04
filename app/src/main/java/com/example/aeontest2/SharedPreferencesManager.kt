package com.example.aeontest2

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        with(sharedPreferences.edit()) {
            putString("TOKEN_KEY", token)
            apply()
        }
    }

    fun getToken(): String {
        return sharedPreferences.getString("TOKEN_KEY", "") ?: ""
    }

    fun clearToken() {
        with(sharedPreferences.edit()) {
            remove("TOKEN_KEY")
            apply()
        }
    }
}