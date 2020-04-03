package br.com.igguerra.animeapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igguerra.animeapp.data.AnimeRepository
import br.com.igguerra.animeapp.model.Anime
import br.com.igguerra.animeapp.model.AnimeItem
import br.com.igguerra.animeapp.model.AnimeSearchResponse
import br.com.igguerra.animeapp.network.Outcome

class AnimeViewModel(private val repository: AnimeRepository): ViewModel() {
    val topList by lazy {
        repository.animeTopList
    }

    val anime by lazy {
        repository.anime
    }

    val animeFavs by lazy {
        repository.animeFavs
    }

    val searchResult: LiveData<Outcome<AnimeSearchResponse>> by lazy {
        repository.searchResult
    }

    fun addAnime(anime: AnimeItem) {
        repository.addAnime(anime, viewModelScope)
    }

    fun deleteAnime(anime: AnimeItem) {
        repository.deleteAnime(anime, viewModelScope)
    }

    fun getTopAnimes() {
        repository.getTopAnimes(viewModelScope)
    }

    fun getAnimeById(id: Int) {
        repository.getAnimeById(id, viewModelScope)
    }

    fun searchAnime(query: String) {
        repository.searchAnime(query, viewModelScope)
    }
}