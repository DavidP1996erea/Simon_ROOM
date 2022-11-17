import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simon.R
import com.example.simon.SimonEntity

class SimonAdapter(
    val listaJugadores: List< SimonEntity>,

) : RecyclerView.Adapter< SimonAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listaJugadores[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.items, parent, false))
    }

    override fun getItemCount(): Int {
        return listaJugadores.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvJugadores = view.findViewById<TextView>(R.id.tvNombreJugadores)
        val tvPulsaciones = view.findViewById<TextView>(R.id.tvPulsacionesJugadores)


        fun bind(
            task: SimonEntity

        ) {
            tvJugadores.text = "- " + task.nombreJugador
            tvPulsaciones.text = " Pulsaciones: "+  task.numPulsaciones.toString()

        }
    }
}
