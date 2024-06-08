package com.example.spotify_history_scrobbler_compose_front.data.models

data class CoverArtArchiveResponse(
    val images: List<CoverArtImage>
)

data class CoverArtImage(
    val image: String,
    val thumbnails: Map<String, String>
)
