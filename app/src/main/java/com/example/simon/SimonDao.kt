package com.example.simon

import androidx.room.*

@Dao
interface SimonDao {

    @Query("Select * from jugador_Datos order by  numPulsaciones desc")
   suspend fun mostrarTodosDatos() : MutableList<SimonEntity>

    @Query("Select * from jugador_Datos where nombreJugador = :nombreJugador")
    suspend fun mostrarJugador(nombreJugador:String) : SimonEntity




    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertJugador(jugador:SimonEntity)

    @Update
    suspend fun updateJugador(jugador:SimonEntity)

    @Delete
    suspend fun deleteJugador(jugador: SimonEntity)
}