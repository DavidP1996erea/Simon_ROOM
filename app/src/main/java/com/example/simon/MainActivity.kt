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

class MainActivity : AppCompatActivity(), comunicador {


    private lateinit var roomDB: SimonDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        roomDB = Room.databaseBuilder(
            this.applicationContext,
            SimonDataBase::class.java,
            "simonDataBase"
        ).build()

        mostrarNombre()



    }

//
    fun mostrarNombre(){

        GlobalScope.launch(Dispatchers.IO) {

            val jugador = roomDB.SimonDao().mostrarJugador(10)

            var meterTexto = findViewById<TextView>(R.id.etnombreJugador)


            meterTexto.text = jugador.nombreJugador


        }
    }


    fun updateNumeroPulsacionesJugador(){

        GlobalScope.launch(Dispatchers.IO) {

            val jugador = roomDB.SimonDao().mostrarJugador(10)

            jugador.numPulsaciones++

            roomDB.SimonDao().updateJugador(jugador)

        }
    }


    /**
     * Se crean las listas necesarias para guardar las imagenes, tanto las del jugador como
     * el de la máquina
     */
    var listaMaquina = ArrayList<ImageView>()
    var listaJugador = ArrayList<ImageView>()

    /**
     * Se crea un contador que controlará las partidas que el usuario ha ganado
     */
    var contadorRondas =0


    /**
     * En este método se crea un array de las imagenes con los diferentes colores, y retorna
     * una de ellas aleatoriamente
     *
     * Poscondición: Devuelve una variable tipo Image aleatoria del array
     */
    fun generarImagenAleatoria() : ImageView{
        var listaFotosJuego = arrayOf(
            findViewById<ImageView>(R.id.parteRojaColor), findViewById(R.id.parteVerdeColor),
            findViewById(R.id.parteAzulColor), findViewById(R.id.parteAmarillaColor)
        )

        return listaFotosJuego[(0..3).random()]

    }


    /**
     * Con este método se iluminan las imagenes, que en este caso sería cambiar la dirección
     * Drawable por otra imagen ilumininada. Primero se crean 4 variables con cada una de
     * las imagenes, luego se crea un switch que tiene como parámetro una imagen
     * que se obtiene como parámetro del método. Dependiendo de que imagen sea llamará a un
     * método u otro que iluminará la imagen correspondiente.
     *
     * Precondición: Se requiere un variable de tipo Image
     * Poscondición: La imagen llamará al método correspondiente
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

    // Parte de la máquina:
    /**
     * Primero se crearán 4 métodos correspondientes a la máquina, cuando sea el turno de la
     * máquina llamará a uno de los 4 métodos siguientes, iluminando la imagen que haya tocado
     * en el random. Para que el cambio de imagen sea visible, se deja un delay entre el cambio
     * de imagen iluminada y la vuelta a la original. Para todo esto, se crea una variable
     * con la imagen necesaria, y luego se cambia la imagen raiz por la iluminada. Por último,
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
     * Igual que el método anterior pero con la imagen roja
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
     * Igual que el método anterior pero con la imagen azul
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
     * Igual que el método anterior pero con la imagen verde
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
     * Igual que los método anteriores, pero se activan cuando el usuario presiona las imagenes.
     * Cada vez que se entra en uno de los 4 métodos siguientes, la imagen que se ha seleccionado
     * se mete en la lista del jugador. Tras introducirlo en la lista, se comprueba que
     * las listas tienen la misma longitud, en caso de que esto ocurra, significa que el usuario
     * ha intentado seguir la secuencia de la máquina, por lo que llama a un método que comprueba
     * que ambas listas son iguales, lo que significaría que el usuario pasa de ronda.
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
         * LLamada al método de comprobar que las listas son iguales
         */
        player.start()

        if (listaMaquina.size == listaJugador.size) {
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    comprobarListas()
                },
                800  )
        }
        updateNumeroPulsacionesJugador()

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
        updateNumeroPulsacionesJugador()
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

        updateNumeroPulsacionesJugador()
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

        updateNumeroPulsacionesJugador()

    }


    /**
     * Este método se activa cuando se pulsa el botón de comenzar partida. Lo primero que se hace
     * es retirar el botón, luego se guarda en una variable uno de las imagenes aleatorias,
     * esto se consigue llamando al método explicado anteriormente. Luego se vacía la lista
     * del jugador, para que en cada ronda el usuario deba meter la secuencia completa. También
     * se añade en la lista de la máquina, la imagen generada aleatoriamente. Por último, se
     * crea un foreach que recorre la lista de la máquina, que iluminará cada una de las imagenes
     * para que el usuario sepa que secuencia debe seguir. Se deja un tiempo entre iteración para
     * que sea perceptible, tambien antes de la iteración para que en caso de que toque la misma
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

    }

    /**
     * Este método es llamado cada  vez que el usuario pulsa una imagen y ambas listas tienen
     * el mismo tamaño. Si ambas listas son iguales significa que el usuario introdució
     * la secuencia correcta, por lo que el contador de rondas aumenta en 1, el texto
     * se cambia para mostrar las rondas ganadas, y se vuelve a llamar al método
     * empezarRonda, ya que ha ganado y todo vuelve a empezar.
     *
     * En caso de que ambas listas no son iguales, se muestra un mensaje de derrota en pantalla, el
     * botón de empezar partida vuelve a aparecer para que se pueda volver a jugar, también
     * se vacía la lista de la máquina, ya que es una ronda completamente nueva, al igual que se
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

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Has perdido...😦😵!")
        builder.setMessage("Has perdido ante la máquina, adiós")
        builder.setPositiveButton("Aceptar", null)

        val mostrarVictoria = builder.create()

        return mostrarVictoria
    }

}