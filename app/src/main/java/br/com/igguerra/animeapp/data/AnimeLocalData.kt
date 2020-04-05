package br.com.igguerra.animeapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.igguerra.animeapp.database.AnimeDataMapper
import br.com.igguerra.animeapp.database.dao.AnimeDao
import br.com.igguerra.animeapp.model.Anime

class AnimeLocalData (private val dao: AnimeDao) {

    fun getAnimeFavs(): LiveData<List<Anime>> {
        return dao.getAll().map {animeList ->
            animeList.map {
                AnimeDataMapper.dtoToAnime(it)
            }
        }
    }

    suspend fun insert(anime: Anime) {
        return dao.insert(AnimeDataMapper.animeToDto(anime))
    }

    suspend fun delete(anime: Anime) {
        return dao.delete(AnimeDataMapper.animeToDto(anime))
    }
}