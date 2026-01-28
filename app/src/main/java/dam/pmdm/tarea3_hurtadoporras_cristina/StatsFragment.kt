package dam.pmdm.tarea3_hurtadoporras_cristina

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.FragmentStatsBinding

class StatsFragment : Fragment() {

    private lateinit var binding: FragmentStatsBinding
    private val episodeViewModel: EpisodeViewModel by viewModels({requireActivity()})

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeViewModel.loadEpisodes()

        episodeViewModel.episodes.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty()) return@observe
            val total = list.size
            val viewed = list.count { it.viewed }
            val percentage = if (total > 0) (viewed * 100 / total) else 0

            binding.stats.text = getString(R.string.stats_texto1, viewed, total)
            binding.stats100.text = getString(R.string.stats_texto2, percentage)
            binding.progressBar.progress = percentage
        }

    }


}