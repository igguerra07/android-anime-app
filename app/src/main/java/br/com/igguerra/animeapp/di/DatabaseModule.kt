package br.com.igguerra.animeapp.di

import androidx.room.Room
import br.com.igguerra.animeapp.database.AnimeDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AnimeDatabase::class.java, "AnimeDB")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
    }

    single {
        get<AnimeDatabase>().animeDao()
    }
}