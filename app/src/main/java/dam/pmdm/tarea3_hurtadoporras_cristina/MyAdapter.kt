import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.Episode
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.EpisodeItemBinding

class MyAdapter (
    private var episodesList: List<Episode>,
    private val onEpisodeClick: (Episode) -> Unit
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    fun submitList(newItems: List<Episode>) {
        episodesList = newItems
        notifyDataSetChanged()
    }

    /**
     * Infla la vista de cada elemento del RecyclerView.
     *
     * @param parent ViewGroup.
     * @param viewType Tipo de vista.
     * @return Un MyViewHolder que contiene la vista inflada.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = EpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    /**
     * Enlaza los datos de cada Episodio con la vista del ViewHolder.
     *
     * @param holder ViewHolder.
     * @param position Posición de cada elemento en la lista.
     */
    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.bin(episodesList[position])
    }

    /**
     * Devuelve el número total de episodios en la lista.
     *
     * @return Tamaño de la lista.
     */
    override fun getItemCount(): Int {
        return episodesList.size
    }

    /**
     * Clase del ViewHolder.
     *
     * @property binding Binding para cada item de Pikmin.
     */
    inner class MyViewHolder(val binding: EpisodeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bin (episode: Episode){
            binding.episodeCode.text = episode.episode
            binding.episodeAirdate.text = episode.airDate

            binding.episodeViewed.visibility =
                if (episode.viewed) View.VISIBLE else View.INVISIBLE

            binding.root.setOnClickListener {
                onEpisodeClick(episode)
            }
        }
    }
}