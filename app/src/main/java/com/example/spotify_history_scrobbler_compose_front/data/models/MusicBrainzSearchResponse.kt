package com.example.spotify_history_scrobbler_compose_front.data.models

data class MusicBrainzSearchResponse(
    val releases: List<MusicBrainzRelease>
)

data class MusicBrainzRelease(
    val id: String,
    val title: String,
    val artist: String
)
