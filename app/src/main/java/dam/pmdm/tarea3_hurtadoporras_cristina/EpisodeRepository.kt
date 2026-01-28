package dam.pmdm.tarea3_hurtadoporras_cristina

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.Episode
import dam.pmdm.tarea3_hurtadoporras_cristina.data.model.EpisodeFirestore

class EpisodeRepository {
    val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun userEpisodesRef() =
        db.collection("episodios")
            .document(auth.currentUser!!.uid)
            .collection("episodio")


    fun saveEpisodeAsViewed(episode: Episode) {
        val episodeData = hashMapOf(
            "name" to episode.name,
            "episode" to episode.episode,
            "air_date" to episode.airDate,
            "characters" to episode.characters,
            "viewed" to true
        )

        userEpisodesRef()
            .document(episode.episode) // S01E01 como ID
            .set(episodeData)
    }

    fun saveEpisodeAsNotViewed(episode: Episode) {
        val episodeData = hashMapOf(
            "name" to episode.name,
            "episode" to episode.episode,
            "air_date" to episode.airDate,
            "characters" to episode.characters,
            "viewed" to false
        )

        userEpisodesRef()
            .document(episode.episode) // S01E01 como ID
            .set(episodeData)
    }

    fun getAllEpisodesWithViewed(
        apiEpisodes: List<Episode>,
        onResult: (List<Episode>) -> Unit
    ) {
        userEpisodesRef().get().addOnSuccessListener { snapshot ->
            val viewedEpisodes = snapshot.toObjects(EpisodeFirestore::class.java)
            val merged = apiEpisodes.map { apiEp ->
                val isViewed = viewedEpisodes.any { it.episode == apiEp.episode && it.viewed }
                apiEp.copy(viewed = isViewed)
            }
            onResult(merged)
        }
    }
}
