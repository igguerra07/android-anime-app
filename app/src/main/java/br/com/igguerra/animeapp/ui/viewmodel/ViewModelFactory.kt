package br.com.igguerra.animeapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.igguerra.animeapp.application.AnimeApplication
import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.data.AnimeLocalData
import br.com.igguerra.animeapp.data.AnimeRemoteData
import br.com.igguerra.animeapp.data.AnimeRepository
import br.com.igguerra.animeapp.database.AnimeDatabase
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Suppress("UNCHECKED_CAST")


class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {

    private val database by lazy { AnimeDatabase.getInstance(AnimeApplication.getInstance()) }
    private val animeLocalData by lazy { AnimeLocalData(database.animeDao()) }
    private val retrofitClient by lazy { InitRetrofit.retrofitClient }
    private val animeRemoteData by lazy { AnimeRemoteData(retrofitClient) }
    private val animeRepository by lazy { AnimeRepository(animeRemoteData, animeLocalData) }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnimeViewModel(animeRepository) as T
    }

    object InitRetrofit {
        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .callTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        val retrofitClient: Retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}