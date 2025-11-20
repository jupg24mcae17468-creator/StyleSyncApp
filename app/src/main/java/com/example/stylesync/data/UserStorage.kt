package com.example.stylesync.data

import android.content.Context
import com.example.stylesync.data.entities.UserEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserStorage(private val context: Context) {
    private val prefs = context.getSharedPreferences("stylesync_users", Context.MODE_PRIVATE)
    private val gson = Gson()

    private fun readUsers(): MutableList<UserEntity> {
        val json = prefs.getString("users_json", null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<UserEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun writeUsers(list: List<UserEntity>) {
        val json = gson.toJson(list)
        prefs.edit().putString("users_json", json).apply()
    }

    @Synchronized
    fun getUserByEmail(email: String): UserEntity? {
        return readUsers().find { it.email.equals(email, ignoreCase = true) }
    }

    @Synchronized
    fun insertUser(user: UserEntity): Long {
        val list = readUsers()
        val newId = (list.maxOfOrNull { it.id } ?: 0L) + 1L
        val toInsert = user.copy(id = newId)
        list.add(toInsert)
        writeUsers(list)
        return newId
    }
}

