package dam.pmdm.tarea3_hurtadoporras_cristina.data.api

import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.CharacterRM
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.EpisodesApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("episode")
    suspend fun getEpisodes(): EpisodesApiResponse

    @GET("character/{ids}")
    suspend fun getCharacters(@Path("ids") ids: String): List<CharacterRM>
}