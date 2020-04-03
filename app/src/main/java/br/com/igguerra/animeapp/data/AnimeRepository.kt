package br.com.igguerra.animeapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.igguerra.animeapp.application.BaseRepository
import br.com.igguerra.animeapp.database.entity.Anime
import br.com.igguerra.animeapp.model.AnimeItem
import br.com.igguerra.animeapp.network.Outcome
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeRepository(private val remote: AnimeRemoteData, private val local: AnimeLocalData): BaseRepository() {
    val animeTopList: MutableLiveData<Outcome<AnimeTopResponse>> = MutableLiveData()
    val anime: MutableLiveData<Outcome<AnimeResponse>> = MutableLiveData()
    val searchResult: MutableLiveData<Outcome<AnimeSearchResponse>> = MutableLiveData()

    val animeFavs: LiveData<List<AnimeItem>> = local.getAnimeFavs()

     fun addAnime(anime: AnimeItem, scope: CoroutineScope) {
       scope.launch {
           withContext(Dispatchers.Default) {
               local.insert(anime)
           }
       }
    }
     fun deleteAnime(anime: AnimeItem, scope: CoroutineScope) {
       scope.launch {
           withContext(Dispatchers.Default) {
               local.delete(anime)
           }
       }
    }

    fun getTopAnimes(scope: CoroutineScope) {
        scope.launch {
            animeTopList.postValue(Outcome.loading())
            val response = safeApiCall { remote.getTopAnimes() }
            animeTopList.postValue(response)
        }
    }

    fun getAnimeById(id: Int, scope: CoroutineScope) {
        scope.launch {
            anime.postValue(Outcome.loading())
            val response = safeApiCall { remote.getAnimeById(id) }
            anime.postValue(response)
        }
    }

    fun searchAnime(query: String, scope: CoroutineScope) {
        scope.launch {
            searchResult.postValue(Outcome.loading())
            val response = safeApiCall { remote.searchAnime(query) }
            searchResult.postValue(response)
        }
    }
}