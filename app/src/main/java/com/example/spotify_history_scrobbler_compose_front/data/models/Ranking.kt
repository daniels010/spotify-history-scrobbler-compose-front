package com.example.spotify_history_scrobbler_compose_front.data.models
data class Ranking(
    val name: String,
    val name1: String,
    val reproductions: Int,
    //TODO: STILL testing from here onwards
    val imageUrl: String,
    val isLoading: Boolean = true // fake loading related
)

/*
data class RankingWithImage(
    val name: String,
    val reproductions: Int,
    val imageUrl: String
)
*/




/*BETA TESTING

data class Ranking(
    val artistName: String,
    val albumName: String,
    val trackName: String,
    val reproductions: Int,
    //testing for here onwards
    val imageUrl: String
)

*/