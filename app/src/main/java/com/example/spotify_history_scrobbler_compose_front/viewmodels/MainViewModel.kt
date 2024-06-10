package com.example.spotify_history_scrobbler_compose_front.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import com.example.spotify_history_scrobbler_compose_front.network.MusicBrainzRetrofitClient
import com.example.spotify_history_scrobbler_compose_front.network.RetrofitClient
import com.example.spotify_history_scrobbler_compose_front.network.CoverArtArchiveRetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log
import com.example.spotify_history_scrobbler_compose_front.R

class MainViewModel : ViewModel() {

    private val api = RetrofitClient.api
    private val musicBrainzApi = MusicBrainzRetrofitClient.api
    private val coverArtApi = CoverArtArchiveRetrofitClient.api

    private val _data = MutableStateFlow<List<Ranking>>(emptyList())
    val data: StateFlow<List<Ranking>> = _data

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isIncrementalLoading = MutableStateFlow(false)
    val isIncrementalLoading: StateFlow<Boolean> = _isIncrementalLoading

    private val pageSize = 5
    private var currentPage = 0

    init {
        loadMore()
    }

    fun loadMore() {
        if (_isIncrementalLoading.value) return

        _isIncrementalLoading.value = true
        viewModelScope.launch {
            try {
                val allRankings = api.fetchAlbums()
                val start = currentPage * pageSize
                val end = minOf(start + pageSize, allRankings.size)
                val nextRankings = allRankings.subList(start, end).map { ranking ->
                    Ranking(
                        name = ranking.name,
                        name1 = ranking.name1,
                        reproductions = ranking.reproductions,
                        //used placeholder initially
                        //old placeholder link: https://via.placeholder.com/150
                        //subbed by local placeholder img
                        imageUrl = "android.resource://com.example.spotify_history_scrobbler_compose_front/${R.drawable.placeholder}"
                    )
                }
                currentPage++

                // fetch album cover images for the new items
                val updatedRankings = nextRankings.map { ranking ->
                    val imageUrl = getAlbumCoverUrl(ranking.name, ranking.name1)
                    ranking.copy(imageUrl = imageUrl)
                }

                // Update the data state with the new rankings with images
                _data.value += updatedRankings

            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching albums", e)
            } finally {
                _isIncrementalLoading.value = false
            }
        }
    }

    private suspend fun getAlbumCoverUrl(albumName: String, artistName: String): String {
        return withContext(Dispatchers.IO) {
            try {
                // Simplificar nome do álbum se tiver versão especial
                val simplifiedAlbumName = if (albumName.contains(":") || albumName.contains("-")) {
                    albumName.split(Regex("[:\\-]")).firstOrNull() ?: albumName
                } else {
                    albumName
                }
                val query = "artist:\"$artistName\" AND release:\"$simplifiedAlbumName\""
                val searchResponse = musicBrainzApi.searchRelease(query = query)
                for (release in searchResponse.releases) {
                    val releaseId = release.id
                    try {
                        val coverArtResponse = coverArtApi.getCoverArt(releaseId)
                        if (coverArtResponse.images.isNotEmpty()) {
                            return@withContext "https://coverartarchive.org/release/$releaseId/front-250.jpg"
                        }
                    } catch (e: retrofit2.HttpException) {
                        if (e.code() == 404) {
                            Log.e("MainViewModel", "Cover art not found (404) for release ID: $releaseId")
                        } else {
                            Log.e("MainViewModel", "HTTP error fetching cover art for release ID: $releaseId", e)
                        }
                    } catch (e: Exception) {
                        Log.e("MainViewModel", "Error fetching cover art for release ID: $releaseId", e)
                    }
                }
                "android.resource://com.example.spotify_history_scrobbler_compose_front/${R.drawable.placeholder}"
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error fetching album cover URL for $albumName", e)
                "android.resource://com.example.spotify_history_scrobbler_compose_front/${R.drawable.placeholder}"
            }
        }
    }
}
