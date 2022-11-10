package com.example.simon

import androidx.room.*


@Dao
interface SimonDao {


    @Query("SELECT * FROM jugador_entity")
    suspend fun getAllTasks(): MutableList <SimonEntity>

    @Query("SELECT * FROM jugador_entity WHERE id=:id")
    suspend  fun getTaskById(id: Long): SimonEntity


    @Insert
    suspend fun addTask(taskEntity : SimonEntity):Long


    @Update
    suspend fun updateTask(task: SimonEntity)


    @Delete
    suspend fun deleteTask(task: SimonEntity)

}