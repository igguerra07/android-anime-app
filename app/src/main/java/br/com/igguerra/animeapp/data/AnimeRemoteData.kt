package br.com.igguerra.animeapp.data

import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import br.com.igguerra.animeapp.network.AnimeApi
import retrofit2.Retrofit

class AnimeRemoteData(private val retrofit: Retrofit) {
    private val api by lazy {
        retrofit.create(AnimeApi::class.java)
    }

    suspend fun getTopAnimes(): AnimeTopResponse {
        return api.getTopAnimes()
    }

    suspend fun getAnimeById(id: Int): AnimeResponse {
        return api.getAnimeById(id.toString())
    }

    suspend fun searchAnime(query: String): AnimeSearchResponse {
        return api.searchAnime(query)
    }
}