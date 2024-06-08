package com.example.spotify_history_scrobbler_compose_front.api.services

import com.example.spotify_history_scrobbler_compose_front.data.models.MusicBrainzSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicBrainzApiService {
    @GET("/ws/2/release/")
    suspend fun searchRelease(
        @Query("query") query: String,
        @Query("fmt") format: String = "json"
    ): MusicBrainzSearchResponse
}
