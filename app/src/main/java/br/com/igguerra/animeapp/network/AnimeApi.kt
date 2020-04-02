package br.com.igguerra.animeapp.network

import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AnimeApi {
    @GET(Constants.TOP_URL)
    suspend fun getTopAnimes(): AnimeTopResponse

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: String): AnimeResponse
}