package com.example.spotify_history_scrobbler_compose_front.api.services


import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import retrofit2.http.GET

interface ApiService {

    @GET("/artist-ranking")
    suspend fun fetchArtists(): List<Ranking>

    @GET("/album-ranking")
    suspend fun fetchAlbums(): List<Ranking>

    @GET("/track-ranking")
    suspend fun fetchTracks(): List<Ranking>


}
