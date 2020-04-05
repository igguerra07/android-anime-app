package br.com.igguerra.animeapp.di

import br.com.igguerra.animeapp.data.AnimeLocalData
import br.com.igguerra.animeapp.data.AnimeRemoteData
import br.com.igguerra.animeapp.data.AnimeRepository
import br.com.igguerra.animeapp.ui.viewmodel.AnimeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val animeModule = module {
    single {
        AnimeRepository(remote = get(), local = get())
    }

    single {
        AnimeRemoteData(api = get())
    }

    single {
        AnimeLocalData(dao = get())
    }

    viewModel {
        AnimeViewModel(repository = get())
    }
}