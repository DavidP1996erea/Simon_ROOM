package com.example.simon

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SimonEntity::class], version = 1)
abstract class SimonDataBase: RoomDatabase() {


    abstract fun SimonDao() : SimonDao

/*
    companion object{

        @Volatile
        private var INSTANCE : SimonDataBase? = null

        fun getDataBase(context: Context): SimonDataBase{

            val tempInstance = INSTANCE
            if (tempInstance !=null){
                return tempInstance
            }
            synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SimonDataBase::class.java,
                    "simonDataBase"
                ).build()
                INSTANCE = instance
                return instance

            }

        }

    }
*/
}