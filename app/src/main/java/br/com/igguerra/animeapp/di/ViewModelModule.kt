package br.com.igguerra.animeapp.di

import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AnimeViewModel(repository = get()) }
}