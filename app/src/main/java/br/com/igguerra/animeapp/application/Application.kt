package br.com.igguerra.animeapp.application

import android.app.Application
import br.com.igguerra.animeapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AnimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AnimeApplication)
            modules(listOf(appModule, imageModule, networkModule, databaseModule, animeModule))
        }
    }
}