package com.example.spotify_history_scrobbler_compose_front.api.services


import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import retrofit2.http.GET

interface ApiService {
    @GET("/album-ranking")
    suspend fun fetchData(): List<Ranking>
}
