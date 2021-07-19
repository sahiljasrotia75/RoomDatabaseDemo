package com.example.todoapplication

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Patterns
import androidx.room.Room

object Constant {


    val isLogin: String = "false"

    var db: AppDatabase? = null
    //----------------------------------shared Prefrence-------------------------------//

    fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("Login", Context.MODE_PRIVATE)//table name
    }

    //Email validate code
    fun isValidEmailId(email: String): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }


    //----------------------------------------------DataBaseHandling--------------------------

    fun getDataBase(applicationContext: Context): AppDatabase? {
        if (db == null) {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "TrackerActivities"
            ).build()
        } else {
            return db
        }
        return db
    }

}