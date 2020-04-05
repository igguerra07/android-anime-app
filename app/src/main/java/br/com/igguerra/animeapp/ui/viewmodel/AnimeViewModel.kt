package br.com.igguerra.animeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igguerra.animeapp.data.AnimeRepository
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.model.AnimeResponse
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.model.AnimeTopResponse
import br.com.igguerra.animeapp.network.Outcome
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AnimeViewModel(private val repository: AnimeRepository): ViewModel() {

    private val _topList: MutableLiveData<Outcome<AnimeTopResponse>> = MutableLiveData()
    val topList: LiveData<Outcome<AnimeTopResponse>> get() = _topList

    private val _anime: MutableLiveData<Outcome<AnimeResponse>> = MutableLiveData()
    val anime: LiveData<Outcome<AnimeResponse>> get() = _anime

    private val _searchResult: MutableLiveData<Outcome<AnimeSearchResponse>> = MutableLiveData()
    val searchResult: LiveData<Outcome<AnimeSearchResponse>> get() = _searchResult

    val animeFavs: LiveData<List<Anime>> by lazy {
        repository.getAnimesFavs()
    }

    fun addAnime(anime: Anime) {
       viewModelScope.launch {
           withContext(Dispatchers.Default){
               repository.addAnime(anime)
           }
       }
    }

    fun deleteAnime(anime: Anime) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                repository.deleteAnime(anime)
            }
        }
    }

    fun getTopAnimes() {
       _topList.value = Outcome.loading()
        viewModelScope.launch {
           val data = repository.getTopAnimes()
            _topList.postValue(data)
       }
    }

    fun getAnimeById(id: Int) {
        _anime.value = Outcome.loading()
        viewModelScope.launch {
            val data = repository.getAnimeById(id)
            _anime.postValue(data)
        }
    }

    fun searchAnime(query: String) {
        _searchResult.value = Outcome.loading()
        viewModelScope.launch {
            val data = repository.searchAnime(query)
            _searchResult.postValue(data)
        }
    }
}