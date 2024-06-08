package com.example.spotify_history_scrobbler_compose_front.network

import com.example.spotify_history_scrobbler_compose_front.api.services.CoverArtArchiveApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CoverArtArchiveRetrofitClient {
    private const val BASE_URL = "https://coverartarchive.org"
    private const val USER_AGENT = "SpotifyHistoryScrobbler/1.0 (danielkc27@gmail.com)"

    private val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val httpClient = OkHttpClient.Builder().addInterceptor(logging).addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .header("User-Agent", USER_AGENT)
            .build()
        chain.proceed(request)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: CoverArtArchiveApiService by lazy {
        retrofit.create(CoverArtArchiveApiService::class.java)
    }
}
