package com.example.newapplication.sharedPref

import android.content.Context
import android.content.SharedPreferences

class sharedPref(context: Context){
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    companion object {
        private val INPUT_USERNAME = "admin"
        private val INPUT_PASSWORD = "admin123"
        private val PREF_NAME = "LoginPref"
        private val USERNAME_KEY = "username"
        private val LOGIN_KEY = "isLoggedIn"
        private val PROFILENAME_KEY = "ProfileName"
    }

    fun saveLogin(username: String) {
        editor.putString(USERNAME_KEY, username)
        editor.putBoolean(LOGIN_KEY, true)
        editor.apply()
    }

    fun validateLogin(username: String, password: String): Boolean {
        return username == INPUT_USERNAME && password == INPUT_PASSWORD
    }

    fun isLoggedIn(): Boolean {
        return sharedPref.getBoolean(LOGIN_KEY, false)
    }

    fun logout() {
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }



    fun getusername(): String? {
        return sharedPref.getString(USERNAME_KEY, "Guest") // Default "Guest" jika username tidak ditemukan
    }

}



