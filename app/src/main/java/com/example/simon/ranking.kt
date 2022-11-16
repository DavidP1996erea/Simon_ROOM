package com.example.simon

import SimonAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

lateinit var recyclerView: RecyclerView
lateinit var adapter: SimonAdapter
lateinit var tasks: MutableList< SimonEntity>

private lateinit var roomDB: SimonDataBase
class ranking : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ranking)



        tasks = ArrayList()
        getTasks()


    }


    fun getTasks()= runBlocking {       // Corrutina que saca de la base de datos la lista de tareas
        launch {                        // Inicio del hilo
            tasks = SimonApp.database.SimonDao().mostrarTodosDatos()     // Se carga la lista de tareas
            setUpRecyclerView(tasks)        // se pasa la lista a la Vista
        }
    }



    fun setUpRecyclerView(tasks: List< SimonEntity>) {    // Método que muestra la vista usando el adaptador
        adapter = SimonAdapter(tasks)
        recyclerView = findViewById(R.id.rvRecycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


}