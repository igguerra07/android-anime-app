package br.com.igguerra.animeapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import br.com.igguerra.animeapp.database.ItemAnimeMapper
import br.com.igguerra.animeapp.database.dao.AnimeDao
import br.com.igguerra.animeapp.database.entity.Anime
import br.com.igguerra.animeapp.model.AnimeItem

class AnimeLocalData (private val dao: AnimeDao) {

    fun getAnimeFavs(): LiveData<List<AnimeItem>> {
        return dao.getAll().map {animeList ->
            animeList.map {
                ItemAnimeMapper.animeToItem(it)
            }
        }
    }

    suspend fun insert(anime: AnimeItem) {
        return dao.insert(ItemAnimeMapper.itemToAnime(anime))
    }

    suspend fun delete(anime: AnimeItem) {
        return dao.delete(ItemAnimeMapper.itemToAnime(anime))
    }
}