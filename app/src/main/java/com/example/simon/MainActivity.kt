package com.example.simon

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
    var contadorRondas =0

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

    override fun jugarParteAmarilla() {

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)

        imagenAmarilla.setImageResource(R.drawable.parteamarillapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                imagenAmarilla.setImageResource(R.drawable.parteamarilla)
            },
            500  )
        listaJugador.add(imagenAmarilla)


        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                2000  )
        }



    }
    override fun jugarParteVerde() {

        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)


        imagenVerde.setImageResource(R.drawable.parteverdepulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
            imagenVerde.setImageResource(R.drawable.parteverde)
            },
            500  )

            listaJugador.add(imagenVerde)




            if (listaMaquina.size == listaJugador.size) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                comprobarListas()
                    },
                    2000  )
            }




    }

    override fun jugarParteAzul() {
        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)

        imagenAzul.setImageResource(R.drawable.parteazulpulsada)


        Handler(Looper.getMainLooper()).postDelayed(
            {
            imagenAzul.setImageResource(R.drawable.parteazul)
            },
            500  )

            listaJugador.add(imagenAzul)


            if (listaMaquina.size == listaJugador.size) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        comprobarListas()
                    },
                    2000  )
            }



    }

    override fun jugarParteRoja() {
        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)

        imagenRoja.setImageResource(R.drawable.parterojapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
            imagenRoja.setImageResource(R.drawable.parteroja)

            },
            500  )
        listaJugador.add(imagenRoja)


            if (listaMaquina.size == listaJugador.size) {
                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        comprobarListas()
                    },
                    2000  )
            }



    }


    override fun empezarRonda() {

        var botonComenzar = findViewById<Button>(R.id.empezarPartida)

        botonComenzar.visibility = View.GONE

        var imagenAleatoria = generarImagenAleatoria()
        listaJugador.clear()
        listaMaquina.add(imagenAleatoria)


        Thread {
            listaMaquina.forEach {
                iluminarBoton(it)
                Thread.sleep(2000)
            }
        }.start()

    }




    fun comprobarListas(){

        var textoContador = findViewById<TextView>(R.id.contadorRondas)


        if(listaMaquina==listaJugador) {
            contadorRondas++;
            textoContador.text = contadorRondas.toString()
            empezarRonda()

        }else{
            mensajeDerrota().show()
            var botonComenzar = findViewById<Button>(R.id.empezarPartida)

            botonComenzar.visibility = View.VISIBLE
            listaMaquina.clear()
            textoContador.text ="0"
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