package dam.pmdm.tarea3_hurtadoporras_cristina

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.pmdm.tarea3_hurtadoporras_cristina.data.api.RetrofitInstance
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.Episode
import kotlinx.coroutines.launch

class EpisodeViewModel: ViewModel() {
    private val _episodes = MutableLiveData<List<Episode>>(emptyList())
    val episodes: LiveData<List<Episode>> = _episodes

    private val repository = EpisodeRepository()

    fun loadEpisodes() {
        viewModelScope.launch {
            try {
                val apiEpisodes = RetrofitInstance.api.getEpisodes().results
                repository.getAllEpisodesWithViewed(apiEpisodes) { mergedList ->
                    _episodes.postValue(mergedList)
                }
            } catch (e: Exception) {
                _episodes.postValue(emptyList())
            }
        }
    }

    fun toggleEpisodeViewed(episode: Episode) {
        episode.viewed = !episode.viewed
        repository.saveEpisodeAsViewed(episode)
        // Actualiza la lista en vivo
        _episodes.value = _episodes.value?.map {
            if (it.episode == episode.episode) episode else it
        }
    }

    fun toggleEpisodeNotViewed(episode: Episode) {
        episode.viewed = !episode.viewed
        repository.saveEpisodeAsNotViewed(episode)
        // Actualiza la lista en vivo
        _episodes.value = _episodes.value?.map {
            if (it.episode == episode.episode) episode else it
        }
    }
}