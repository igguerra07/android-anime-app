package br.com.igguerra.animeapp.application

import android.app.Application
import com.facebook.stetho.Stetho

class AnimeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Stetho.initializeWithDefaults(this);
    }

    companion object {
        private lateinit var instance: AnimeApplication
        fun getInstance() = instance
    }
}