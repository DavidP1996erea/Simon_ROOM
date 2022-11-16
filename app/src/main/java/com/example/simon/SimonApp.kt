package com.example.simon

import android.app.Application
import androidx.room.Room

class SimonApp: Application() {

    companion object {
        lateinit var database: SimonDataBase
    }

    override fun onCreate() {
        super.onCreate()
        SimonApp.database =  Room.databaseBuilder(this, SimonDataBase::class.java, "simonDataBase").build()
    }
}