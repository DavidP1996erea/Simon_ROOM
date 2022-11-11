package com.example.simon

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity ( tableName = "jugador_Datos")

data class SimonEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int=0,
    var nombreJugador:String="",
    var numPulsaciones:Int=0,



    )
