package br.com.igguerra.animeapp.data

import androidx.lifecycle.MutableLiveData
import br.com.igguerra.animeapp.application.BaseRepository
import br.com.igguerra.animeapp.network.Outcome
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AnimeRepository(private val remote: AnimeRemoteData): BaseRepository() {
    val animeTopList: MutableLiveData<Outcome<AnimeTopResponse>> = MutableLiveData()
    val anime: MutableLiveData<Outcome<AnimeResponse>> = MutableLiveData()

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
}