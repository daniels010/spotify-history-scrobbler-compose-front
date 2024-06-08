package com.example.spotify_history_scrobbler_compose_front.api.services

import com.example.spotify_history_scrobbler_compose_front.data.models.CoverArtArchiveResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoverArtArchiveApiService {
    @GET("/release/{releaseId}")
    suspend fun getCoverArt(
        @Path("releaseId") releaseId: String
    ): CoverArtArchiveResponse
}
