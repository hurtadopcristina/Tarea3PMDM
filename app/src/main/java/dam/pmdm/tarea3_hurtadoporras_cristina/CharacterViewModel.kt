package dam.pmdm.tarea3_hurtadoporras_cristina

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.pmdm.tarea3_hurtadoporras_cristina.data.api.RetrofitInstance
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.CharacterRM
import kotlinx.coroutines.launch

class CharacterViewModel: ViewModel() {
    private val _characters = MutableLiveData<List<CharacterRM>>()
    val characters: LiveData<List<CharacterRM>> = _characters

    fun loadCharacters(urls: List<String>) {
        viewModelScope.launch {
            try {
                val ids = urls.map { it.substringAfterLast("/") }.joinToString(",")

                _characters.value = RetrofitInstance.api.getCharacters(ids)
            } catch (e: Exception) {
                _characters.value = emptyList()
            }
        }
    }
}