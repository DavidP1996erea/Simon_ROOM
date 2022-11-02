package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import java.lang.reflect.Array

class MainActivity : AppCompatActivity(), comunicador {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



    }


    var listaMaquina = ArrayList<ImageView>()
    var listaJugador = ArrayList<ImageView>()

    fun generarImagenAleatoria() : ImageView{
        var listaFotosJuego = arrayOf(
            findViewById<ImageView>(R.id.parteRojaColor), findViewById(R.id.parteVerdeColor),
            findViewById(R.id.parteAzulColor), findViewById(R.id.parteAmarillaColor)
        )

        return listaFotosJuego[(0..3).random()]

    }


    fun iluminarBoton(imagenAleatoria:ImageView){

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)
        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)
        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)
        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)

        when (imagenAleatoria) {
            imagenAmarilla -> {

                jugarParteAmarillaMaquina()
            }
            imagenVerde -> {

                jugarParteVerdeMaquina()
            }
            imagenRoja -> {

                jugarParteRojaMaquina()
            }
            imagenAzul -> {

                jugarParteAzulMaquina()
            }
        }
    }


    override fun jugarParteAmarilla() {

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)

        imagenAmarilla.setImageResource(R.drawable.parteamarillapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAmarilla.setImageResource(R.drawable.parteamarilla)
            },
            2000 // value in milliseconds
        )
        listaJugador.add(imagenAmarilla)

        if(listaMaquina.size==listaJugador.size){
            comprobarListas()
        }

    }

     fun jugarParteAmarillaMaquina() {

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)

        imagenAmarilla.setImageResource(R.drawable.parteamarillapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAmarilla.setImageResource(R.drawable.parteamarilla)
            },
            2000 // value in milliseconds
        )

    }

    fun jugarParteRojaMaquina() {

        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)

        imagenRoja.setImageResource(R.drawable.parterojapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenRoja.setImageResource(R.drawable.parteroja)
            },
            2000 // value in milliseconds
        )

    }
    fun jugarParteAzulMaquina() {

        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)

        imagenAzul.setImageResource(R.drawable.parteazulpulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAzul.setImageResource(R.drawable.parteazul)
            },
            2000 // value in milliseconds
        )

    }
    fun jugarParteVerdeMaquina() {

        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)

        imagenVerde.setImageResource(R.drawable.parteverdepulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenVerde.setImageResource(R.drawable.parteverde)
            },
            2000 // value in milliseconds
        )

    }

    override fun jugarParteVerde() {

        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)


        imagenVerde.setImageResource(R.drawable.parteverdepulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenVerde.setImageResource(R.drawable.parteverde)
            },
            2000 // value in milliseconds
        )

        listaJugador.add(imagenVerde)

        if(listaMaquina.size==listaJugador.size){
            comprobarListas()
        }


    }

    override fun jugarParteAzul() {
        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)

        imagenAzul.setImageResource(R.drawable.parteazulpulsada)
        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAzul.setImageResource(R.drawable.parteazul)
            },
            2000 // value in milliseconds
        )

        listaJugador.add(imagenAzul)

        if(listaMaquina.size==listaJugador.size){
            comprobarListas()
        }

    }

    override fun jugarParteRoja() {
        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)

        imagenRoja.setImageResource(R.drawable.parterojapulsada)
        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenRoja.setImageResource(R.drawable.parteroja)
            },
            2000 // value in milliseconds
        )

        listaJugador.add(imagenRoja)

        if(listaMaquina.size==listaJugador.size){
            comprobarListas()
        }

    }


    override fun empezarRonda() {


        var imagenAleatoria = generarImagenAleatoria()
        listaJugador.clear()
        listaMaquina.add(imagenAleatoria)




        listaMaquina.forEach {
                 iluminarBoton(it)
        }


    }




    fun comprobarListas(){


        if(listaMaquina==listaJugador) {

            empezarRonda()
        }else{
            mensajeDerrota().show()
        }


    }

    fun mensajeDerrota(): AlertDialog {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Has perdido...😦😵!")
        builder.setMessage("Has perdido ante la máquina, adiós")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }


}