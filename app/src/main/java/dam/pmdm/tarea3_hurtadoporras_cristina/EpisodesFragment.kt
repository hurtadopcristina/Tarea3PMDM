package dam.pmdm.tarea3_hurtadoporras_cristina

import MyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.Episode
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.FragmentEpisodesBinding
import kotlin.getValue

class EpisodesFragment : Fragment() {

    // Binding del fragmento
    private lateinit var binding: FragmentEpisodesBinding

    private lateinit var myAdapter: MyAdapter
    private val episodeViewModel: EpisodeViewModel by viewModels({requireActivity()})
    private var showViewed = false
    private var allEpisodes: List<Episode> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        episodeViewModel.episodes.observe(viewLifecycleOwner) {list ->
            allEpisodes = list
            applyFilter()
        }
        episodeViewModel.loadEpisodes()

        binding.todos.setOnClickListener {
            showViewed = false
            applyFilter()
            updateButtons()
        }

        binding.vistos.setOnClickListener {
            showViewed = true
            applyFilter()
            updateButtons()
        }
    }

    private fun updateButtons() {
        binding.todos.isEnabled = showViewed
        binding.vistos.isEnabled = !showViewed
    }

    private fun applyFilter() {
        val filteredList = if (showViewed) {
            allEpisodes.filter { it.viewed }
        } else {
            allEpisodes
        }
        myAdapter.submitList(filteredList)
    }

    private fun setupRecyclerView() {
        val navController = findNavController()
        myAdapter = MyAdapter(listOf()) { episode ->
            val bundle = Bundle().apply {
                putString("name", episode.name)
                putString("episode", episode.episode)
                putString("airDate", episode.airDate)
                putStringArrayList("characters", ArrayList(episode.characters))
            }
            navController.navigate(R.id.action_episodesFragment_to_detailsFragment, bundle)
        }
        binding.recyclerView.adapter = myAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}