package com.example.simon

import android.app.Activity
import android.media.MediaPlayer
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.simon.databinding.ActivityLoginBinding
import com.example.simon.databinding.ActivityMainBinding
import com.example.simon.databinding.FragmentVerdeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.reflect.Array
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), comunicador {


    private lateinit var roomDB: SimonDataBase

    var pulsacionesPorPartida=0;
    var nombreUsuario="";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        nombreUsuario =  intent.getStringExtra("Nombre").toString()

        roomDB = Room.databaseBuilder(
            this.applicationContext,
            SimonDataBase::class.java,
            "simonDataBase"
        ).build()


    }

    override fun onStart() {
        super.onStart()

        mostrarNombre()



    }


    fun mostrarNombre(){

        GlobalScope.launch(Dispatchers.IO) {

            val jugador = roomDB.SimonDao().mostrarJugador(nombreUsuario)

            var meterTexto = findViewById<TextView>(R.id.etnombreJugador)


            meterTexto.text = jugador.nombreJugador


            var puntuacionMaximaJugador = findViewById<TextView>(R.id.etnumeroPulsacionesMaxima)
            puntuacionMaximaJugador.text=jugador.numPulsaciones.toString()
        }
    }


    fun updateNumeroPulsacionesJugador(){

        GlobalScope.launch(Dispatchers.IO) {

            val jugador = roomDB.SimonDao().mostrarJugador(nombreUsuario)

            if(pulsacionesPorPartida>jugador.numPulsaciones){
                jugador.numPulsaciones=pulsacionesPorPartida


                roomDB.SimonDao().updateJugador(jugador)
            }



        }
    }


    /**
     * Se crean las listas necesarias para guardar las imagenes, tanto las del jugador como
     * el de la m??quina
     */
    var listaMaquina = ArrayList<ImageView>()
    var listaJugador = ArrayList<ImageView>()

    /**
     * Se crea un contador que controlar?? las partidas que el usuario ha ganado
     */
    var contadorRondas =0


    /**
     * En este m??todo se crea un array de las imagenes con los diferentes colores, y retorna
     * una de ellas aleatoriamente
     *
     * Poscondici??n: Devuelve una variable tipo Image aleatoria del array
     */
    fun generarImagenAleatoria() : ImageView{
        var listaFotosJuego = arrayOf(
            findViewById<ImageView>(R.id.parteRojaColor), findViewById(R.id.parteVerdeColor),
            findViewById(R.id.parteAzulColor), findViewById(R.id.parteAmarillaColor)
        )

        return listaFotosJuego[(0..3).random()]

    }


    /**
     * Con este m??todo se iluminan las imagenes, que en este caso ser??a cambiar la direcci??n
     * Drawable por otra imagen ilumininada. Primero se crean 4 variables con cada una de
     * las imagenes, luego se crea un switch que tiene como par??metro una imagen
     * que se obtiene como par??metro del m??todo. Dependiendo de que imagen sea llamar?? a un
     * m??todo u otro que iluminar?? la imagen correspondiente.
     *
     * Precondici??n: Se requiere un variable de tipo Image
     * Poscondici??n: La imagen llamar?? al m??todo correspondiente
     */

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

    // Parte de la m??quina:
    /**
     * Primero se crear??n 4 m??todos correspondientes a la m??quina, cuando sea el turno de la
     * m??quina llamar?? a uno de los 4 m??todos siguientes, iluminando la imagen que haya tocado
     * en el random. Para que el cambio de imagen sea visible, se deja un delay entre el cambio
     * de imagen iluminada y la vuelta a la original. Para todo esto, se crea una variable
     * con la imagen necesaria, y luego se cambia la imagen raiz por la iluminada. Por ??ltimo,
     * tras el delay se vuelve a cambiar a la imagen original
     */
    fun jugarParteAmarillaMaquina() {
        var player= MediaPlayer.create(this,R.raw.segundosonido)
        player.start()

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)

        imagenAmarilla.setImageResource(R.drawable.parteamarillapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAmarilla.setImageResource(R.drawable.parteamarilla)
            },
            800 )

    }

    /**
     * Igual que el m??todo anterior pero con la imagen roja
     */
    fun jugarParteRojaMaquina() {

        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)
        var player= MediaPlayer.create(this,R.raw.primersonido)
        player.start()
        imagenRoja.setImageResource(R.drawable.parterojapulsada)


        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenRoja.setImageResource(R.drawable.parteroja)
            },
            800)

    }

    /**
     * Igual que el m??todo anterior pero con la imagen azul
     */
    fun jugarParteAzulMaquina() {

        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)
        var player= MediaPlayer.create(this,R.raw.tercersonido)
        player.start()
        imagenAzul.setImageResource(R.drawable.parteazulpulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenAzul.setImageResource(R.drawable.parteazul)
            },
            800 )

    }

    /**
     * Igual que el m??todo anterior pero con la imagen verde
     */
    fun jugarParteVerdeMaquina() {

        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)
        var player= MediaPlayer.create(this,R.raw.cuartosonido)
        player.start()
        imagenVerde.setImageResource(R.drawable.parteverdepulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                imagenVerde.setImageResource(R.drawable.parteverde)
            },
            800 )

    }

    // Parte del jugador:

    /**
     * Igual que los m??todo anteriores, pero se activan cuando el usuario presiona las imagenes.
     * Cada vez que se entra en uno de los 4 m??todos siguientes, la imagen que se ha seleccionado
     * se mete en la lista del jugador. Tras introducirlo en la lista, se comprueba que
     * las listas tienen la misma longitud, en caso de que esto ocurra, significa que el usuario
     * ha intentado seguir la secuencia de la m??quina, por lo que llama a un m??todo que comprueba
     * que ambas listas son iguales, lo que significar??a que el usuario pasa de ronda.
     */
    override fun jugarParteAmarilla() {

        var imagenAmarilla = findViewById<ImageView>(R.id.parteAmarillaColor)
        var player= MediaPlayer.create(this,R.raw.segundosonido)

        imagenAmarilla.setImageResource(R.drawable.parteamarillapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                imagenAmarilla.setImageResource(R.drawable.parteamarilla)
            },
            500  )

        listaJugador.add(imagenAmarilla)

        /**
         * LLamada al m??todo de comprobar que las listas son iguales
         */
        player.start()

        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                800  )
        }
        pulsacionesPorPartida++;

    }

    /**
     * Lo mismo que el anterior pero con la imagen verde
     */
    override fun jugarParteVerde() {

        var imagenVerde = findViewById<ImageView>(R.id.parteVerdeColor)
        var player= MediaPlayer.create(this,R.raw.cuartosonido)


        imagenVerde.setImageResource(R.drawable.parteverdepulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                imagenVerde.setImageResource(R.drawable.parteverde)
            },
            500 )

        listaJugador.add(imagenVerde)

        player.start()
        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                800 )
        }
        pulsacionesPorPartida++;
    }

    /**
     * Lo mismo que el anterior pero con la imagen azul
     */

    override fun jugarParteAzul() {
        var imagenAzul = findViewById<ImageView>(R.id.parteAzulColor)
        var player= MediaPlayer.create(this,R.raw.tercersonido)

        imagenAzul.setImageResource(R.drawable.parteazulpulsada)


        Handler(Looper.getMainLooper()).postDelayed(
            {
                imagenAzul.setImageResource(R.drawable.parteazul)
            },
            500  )

        listaJugador.add(imagenAzul)
        player.start()

        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                800  )
        }

        pulsacionesPorPartida++;
    }

    /**
     * Lo mismo que el anterior pero con la imagen roja
     */

    override fun jugarParteRoja() {
        var imagenRoja = findViewById<ImageView>(R.id.parteRojaColor)
        var player= MediaPlayer.create(this,R.raw.primersonido)


        imagenRoja.setImageResource(R.drawable.parterojapulsada)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                imagenRoja.setImageResource(R.drawable.parteroja)

            },
            500  )
        listaJugador.add(imagenRoja)



        player.start()


        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                800  )
        }

        pulsacionesPorPartida++;

    }


    /**
     * Este m??todo se activa cuando se pulsa el bot??n de comenzar partida. Lo primero que se hace
     * es retirar el bot??n, luego se guarda en una variable uno de las imagenes aleatorias,
     * esto se consigue llamando al m??todo explicado anteriormente. Luego se vac??a la lista
     * del jugador, para que en cada ronda el usuario deba meter la secuencia completa. Tambi??n
     * se a??ade en la lista de la m??quina, la imagen generada aleatoriamente. Por ??ltimo, se
     * crea un foreach que recorre la lista de la m??quina, que iluminar?? cada una de las imagenes
     * para que el usuario sepa que secuencia debe seguir. Se deja un tiempo entre iteraci??n para
     * que sea perceptible, tambien antes de la iteraci??n para que en caso de que toque la misma
     * imagen dos veces, se vea visualmente como se apaga y se enciende dos veces, y no que se
     * quede encendida el doble de tiempo.
     */
    override fun empezarRonda() {

        var botonComenzar = findViewById<Button>(R.id.empezarPartida)

        botonComenzar.visibility = View.GONE

        var imagenAleatoria = generarImagenAleatoria()
        listaJugador.clear()
        listaMaquina.add(imagenAleatoria)


        Thread {
            listaMaquina.forEach {
                Thread.sleep(800)
                iluminarBoton(it)
                Thread.sleep(800)
            }
        }.start()

        var puntuacionPartida = findViewById<TextView>(R.id.etnumeroPulsaciones)
        puntuacionPartida.text=pulsacionesPorPartida.toString();
    }

    /**
     * Este m??todo es llamado cada  vez que el usuario pulsa una imagen y ambas listas tienen
     * el mismo tama??o. Si ambas listas son iguales significa que el usuario introduci??
     * la secuencia correcta, por lo que el contador de rondas aumenta en 1, el texto
     * se cambia para mostrar las rondas ganadas, y se vuelve a llamar al m??todo
     * empezarRonda, ya que ha ganado y todo vuelve a empezar.
     *
     * En caso de que ambas listas no son iguales, se muestra un mensaje de derrota en pantalla, el
     * bot??n de empezar partida vuelve a aparecer para que se pueda volver a jugar, tambi??n
     * se vac??a la lista de la m??quina, ya que es una ronda completamente nueva, al igual que se
     * reinicia el contador de rondas ganadas.
     */

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
            contadorRondas =0
            textoContador.text = contadorRondas.toString()
        }


    }

    /**
     * Mensaje de derrota
     */

    fun mensajeDerrota(): AlertDialog {
        updateNumeroPulsacionesJugador()


        val builder = AlertDialog.Builder(this)
        builder.setTitle("Has perdido...????????!")
        builder.setMessage("Has perdido ante la m??quina, adi??s")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()
        pulsacionesPorPartida=0;
        return mostrarVictoria
    }

}