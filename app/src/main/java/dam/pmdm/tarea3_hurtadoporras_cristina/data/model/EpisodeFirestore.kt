package dam.pmdm.tarea3_hurtadoporras_cristina.data.model

data class EpisodeFirestore(
    val name: String = "",
    val episode: String = "",
    val air_date: String = "",
    val characters: List<String> = emptyList(),
    val viewed: Boolean = false
)
