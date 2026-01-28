package dam.pmdm.tarea3_hurtadoporras_cristina

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.CharacterRM
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.CharacterItemBinding

/**
 *
 */
class CharacterAdapter(
    private var charactersList: List<CharacterRM>
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    fun submitList(newItems: List<CharacterRM>) {
        charactersList = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val binding = CharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CharacterViewHolder,
        position: Int
    ) {
        holder.bin(charactersList[position])
    }

    override fun getItemCount(): Int {
        return charactersList.size
    }

    inner class CharacterViewHolder(private val binding: CharacterItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bin (character : CharacterRM) {
            binding.characterName.text = character.name
            binding.characterImage.load(character.image) {
                placeholder(R.drawable.rick)
                error(R.drawable.check)
            }
        }
    }
}