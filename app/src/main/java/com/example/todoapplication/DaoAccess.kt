package com.example.todoapplication

import androidx.room.*

@Dao
interface DaoAccess {

    @Query("SELECT * FROM TodoData")
    fun getAll(): List<TodoData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(freights: TodoData)

    @Query("UPDATE TodoData SET title=:title, description=:desc WHERE time = :time")
    fun updateData(title: String,desc: String, time: String)


    @Delete
    fun delete(task: TodoData)
}