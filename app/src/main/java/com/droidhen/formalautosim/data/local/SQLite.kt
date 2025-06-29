package com.droidhen.formalautosim.data.local

import android.content.ContentValues
import android.database.Cursor
import com.droidhen.formalautosim.core.entities.User
import javax.inject.Inject

class SQLite @Inject constructor(private val database: SQLModel) {

    fun insertUser(user:User): Long {
        val db = database.writableDatabase
        val values = ContentValues().apply {
            put("name", user.getName())
            put("email", user.getLogInData().first)
            put("password", user.getLogInData().second)
        }

        return db.insert("users", null, values)
    }

    fun getUser(): User? {
        val db = database.readableDatabase
        val cursor: Cursor = db.query(
            "users", null, null, null, null, null, null
        )
        var user: User? = null

        with(cursor) {
            while (moveToNext()) {
                user = User()
                val name = getString(getColumnIndexOrThrow("name"))
                val email = getString(getColumnIndexOrThrow("email"))
                val password = getString(getColumnIndexOrThrow("password"))
                user!!.setUserName(name)
                user!!.setEmail(email)
                user!!.setPassword(password)
            }
        }
        cursor.close()
        return user
    }
}

