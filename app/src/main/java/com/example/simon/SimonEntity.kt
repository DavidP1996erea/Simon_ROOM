package com.example.simon

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*

@Entity (tableName = "jugador_entity")
data class SimonEntity (

    @PrimaryKey(autoGenerate = true)
    var id:Int =0,
    var nombreJugador:String="",
    var nPulsaciones:Int =0,
    var fecha : Date,
    var hora :Time

    )



