package br.com.igguerra.animeapp.data

import br.com.igguerra.animeapp.application.BaseRepository
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import br.com.igguerra.animeapp.network.Outcome

class AnimeRepository(private val remote: AnimeRemoteData, private val local: AnimeLocalData) :
    BaseRepository() {

    fun getAnimesFavs() = local.getAnimeFavs()

    suspend fun addAnime(anime: Anime) {
        return local.insert(anime)
    }

    suspend fun deleteAnime(anime: Anime) {
        return local.delete(anime)
    }

    suspend fun getTopAnimes(): Outcome<AnimeTopResponse>? {
        return safeApiCall { remote.getTopAnimes() }
    }

    suspend fun getAnimeById(id: Int): Outcome<AnimeResponse>? {
        return safeApiCall { remote.getAnimeById(id) }
    }

    suspend fun searchAnime(query: String): Outcome<AnimeSearchResponse>? {
        return safeApiCall { remote.searchAnime(query) }
    }
}