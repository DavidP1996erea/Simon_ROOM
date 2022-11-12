package com.example.simon

import androidx.room.*

@Dao
interface SimonDao {

    @Query("Select * from jugador_Datos")
    fun mostrarTodosDatos() : List<SimonEntity>

    @Query("Select * from jugador_Datos where id = :id")
    fun mostrarJugador(id:Int) : SimonEntity




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJugador(jugador:SimonEntity)

    @Update
    suspend fun updateJugador(jugador:SimonEntity)

    @Delete
    suspend fun deleteJugador(jugador: SimonEntity)
}