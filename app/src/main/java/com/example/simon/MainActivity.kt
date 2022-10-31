package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.lang.reflect.Array

class MainActivity : AppCompatActivity(), comunicador {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()



    }



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

                jugarParteAmarilla()
            }
            imagenVerde -> {

                jugarParteVerde()
            }
            imagenRoja -> {

                jugarParteRoja()
            }
            imagenAzul -> {

                jugarParteAzul()
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


    }

    override fun empezarRonda() {

        var imagen = generarImagenAleatoria()

        iluminarBoton(imagen)


    }


}