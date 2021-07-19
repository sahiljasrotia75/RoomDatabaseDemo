package com.example.todoapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TodoData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoAccess(): DaoAccess?
}