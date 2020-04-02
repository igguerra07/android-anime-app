package br.com.igguerra.animeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.igguerra.animeapp.data.AnimeRepository

class AnimeViewModel(private val repository: AnimeRepository): ViewModel() {
    val topList by lazy {
        repository.animeTopList
    }

    val anime by lazy {
        repository.anime
    }

    fun getTopAnimes() {
        repository.getTopAnimes(viewModelScope)
    }

    fun getAnimeById(id: Int) {
        repository.getAnimeById(id, viewModelScope)
    }
}