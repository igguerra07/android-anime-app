package br.com.igguerra.animeapp.di

import com.squareup.picasso.Picasso
import org.koin.dsl.module

val imageModule = module {
    single {
        Picasso.get()
    }
}