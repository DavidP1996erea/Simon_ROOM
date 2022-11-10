package com.example.simon

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [SimonEntity::class], version = 1)

abstract class TasksDatabase : RoomDatabase() {

    abstract fun simonDao():SimonDao



}