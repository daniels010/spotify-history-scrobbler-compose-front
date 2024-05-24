package com.example.spotify_history_scrobbler_compose_front.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import com.example.spotify_history_scrobbler_compose_front.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val api = RetrofitClient.api

    private val _data = MutableStateFlow<List<Ranking>>(emptyList())
    val data: StateFlow<List<Ranking>> = _data

    init {
        fetchAlbums()
    }

    private fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val rankingsFromApi = api.fetchAlbums()
                val rankingsWithImages = rankingsFromApi.map { ranking ->
                    Ranking(
                        name = ranking.name,
                        reproductions = ranking.reproductions,
                        imageUrl = getImageUrlForRanking(ranking.name) // Assign image URL
                    )
                }
                _data.value = rankingsWithImages
            } catch (e: Exception) {
                // Handle exceptions
                _data.value = listOf(Ranking("Error", 0, ""))
            }
        }
    }

    private fun getImageUrlForRanking(name: String): String {
        // Return the URL of the image based on the name of the artist
        return when (name) {
            "Artista 1" ->  "https://ia801309.us.archive.org/28/items/mbid-f268b8bc-2768-426b-901b-c7966e76de29/mbid-f268b8bc-2768-426b-901b-c7966e76de29-12750224075_thumb500.jpg"
            "Artista 2" ->  "https://ia801309.us.archive.org/28/items/mbid-f268b8bc-2768-426b-901b-c7966e76de29/mbid-f268b8bc-2768-426b-901b-c7966e76de29-12750224075_thumb500.jpg"
            else ->  "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF5tqNPhK9IHFnRli_p0qGopUn5bHP7o3uyMINWT5-YQ&s"
        }
    }
}
