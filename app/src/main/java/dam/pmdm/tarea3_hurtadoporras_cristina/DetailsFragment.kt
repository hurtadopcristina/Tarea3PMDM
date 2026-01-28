package dam.pmdm.tarea3_hurtadoporras_cristina

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.Episode
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var myAdapter: CharacterAdapter
    private val charactersViewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val name = it.getString("name") ?: ""
            val episode = it.getString("episode") ?: ""
            val airDate = it.getString("airDate") ?: ""
            val characters = it.getStringArrayList("characters") ?: emptyList()

        binding.detailTitle.text = name
        binding.detailEpisode.text = episode
        binding.detailAirDate.text = airDate
        }

        val charactersUrls = arguments?.getStringArrayList("characters") ?: arrayListOf()
        setupRecyclerView()
        charactersViewModel.characters.observe(viewLifecycleOwner) {list ->
            if (list.isNotEmpty()) {
                myAdapter.submitList(list)
            }
        }
        charactersViewModel.loadCharacters(charactersUrls)

        binding.viewedButton.setOnClickListener {
            val episode = Episode(
                name = binding.detailTitle.text.toString(),
                episode = binding.detailEpisode.text.toString(),
                airDate = binding.detailAirDate.text.toString(),
                characters = charactersViewModel.characters.value?.map { it.name } ?: emptyList(),
                viewed = true
            )
            val episodeViewModel: EpisodeViewModel by viewModels({requireActivity()})
            episodeViewModel.toggleEpisodeViewed(episode)
            Toast.makeText(requireContext(), "Episodio actualizado", Toast.LENGTH_SHORT).show()
        }
        binding.notviewedButton.setOnClickListener {
            val episode = Episode(
                name = binding.detailTitle.text.toString(),
                episode = binding.detailEpisode.text.toString(),
                airDate = binding.detailAirDate.text.toString(),
                characters = charactersViewModel.characters.value?.map { it.name } ?: emptyList(),
                viewed = false // temporal, se actualizará en ViewModel
            )
            val episodeViewModel: EpisodeViewModel by viewModels({requireActivity()})
            episodeViewModel.toggleEpisodeNotViewed(episode)
            Toast.makeText(requireContext(), "Episodio actualizado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerView() {
        myAdapter = CharacterAdapter(listOf())
        binding.recyclerViewDetails.adapter = myAdapter
        binding.recyclerViewDetails.layoutManager = GridLayoutManager(requireContext(), 2)
    }

}