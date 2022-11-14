package com.example.simon


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.room.Room
import com.example.simon.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.contains as contains


class Login : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var roomDB: SimonDataBase
    var login="";
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


        binding.btnLogearse.setOnClickListener{
            logearse()

        }


    }

    private fun escribirDatos(){

        val nombre = binding.etNombre.text.toString()
        login = nombre
        if (nombre.isNotEmpty()){

            val simonJugador= SimonEntity(
                nombre,0
            )

            GlobalScope.launch(Dispatchers.IO){
                roomDB.SimonDao().insertJugador(simonJugador)
            }

            binding.etNombre.text.clear()
        }
    }


    fun logearse(){
        var etiquetaRegistro = findViewById<EditText>(R.id.etNombreRegistro)
        val nombre = etiquetaRegistro.text

        if (nombre.isNotEmpty()){


            GlobalScope.launch(Dispatchers.IO){

                if(roomDB.SimonDao().mostrarTodosDatos().contains(roomDB.SimonDao().mostrarJugador(nombre.toString()))){
                    login = nombre.toString()
                    irSimon()
                }
            }
        }
    }

    fun irSimon(){

        if(login!=""){
            val cambiarPantalla = Intent(this,MainActivity ::class.java)
            cambiarPantalla.putExtra("Nombre", login)
            startActivity(cambiarPantalla)
        }

    }


}