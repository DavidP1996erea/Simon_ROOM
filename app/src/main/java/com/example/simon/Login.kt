package com.example.simon


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import com.example.simon.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var roomDB: SimonDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        roomDB = Room.databaseBuilder(
            this.applicationContext,
            SimonDataBase::class.java,
            "simonDataBase"
        ).build()

        binding.btnLogin.setOnClickListener{

            escribirDatos()
            irSimon()
        }



    }

    private fun escribirDatos(){

        val nombre = binding.etNombre.text.toString()

        if (nombre.isNotEmpty()){

            val simonJugador= SimonEntity(
                0 ,nombre,0
            )

            GlobalScope.launch(Dispatchers.IO){
                roomDB.SimonDao().insertJugador(simonJugador)
            }

            binding.etNombre.text.clear()
        }
    }


    fun irSimon(){

        val cambiarPantalla = Intent(this,MainActivity ::class.java).apply {

        }
        startActivity(cambiarPantalla)
    }


}