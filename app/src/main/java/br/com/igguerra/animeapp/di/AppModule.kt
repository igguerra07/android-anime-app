package br.com.igguerra.animeapp.di

import br.com.igguerra.animeapp.utils.StringProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        StringProvider(androidContext())
    }
}