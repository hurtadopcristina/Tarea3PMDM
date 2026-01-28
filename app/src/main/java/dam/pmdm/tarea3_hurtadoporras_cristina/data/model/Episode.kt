package dam.pmdm.tarea3_hurtadoporras_cristina.data.model

import com.google.gson.annotations.SerializedName

data class Episode (
    val name: String,
    val episode: String,
    var viewed: Boolean = false,
    val characters: List<String>,

    @SerializedName("air_date")
    val airDate: String
)

data class EpisodesApiResponse (
    val results: List<Episode>
)