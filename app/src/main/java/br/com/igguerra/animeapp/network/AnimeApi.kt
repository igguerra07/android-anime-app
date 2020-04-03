package br.com.igguerra.animeapp.network

import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimeApi {
    @GET(Constants.TOP_URL)
    suspend fun getTopAnimes(): AnimeTopResponse

    @GET(Constants.ANIME_URL)
    suspend fun getAnimeById(@Path("id") id: String): AnimeResponse

    @GET(Constants.SEARCH_URL)
    suspend fun searchAnime(@Query("q") query: String): AnimeSearchResponse


}