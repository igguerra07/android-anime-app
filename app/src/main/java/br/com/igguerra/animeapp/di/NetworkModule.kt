package br.com.igguerra.animeapp.di

import br.com.igguerra.animeapp.application.Constants
import br.com.igguerra.animeapp.network.AnimeApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT    = 1L
private const val WRITE_TIMEOUT   = 1L
private const val CONNECT_TIMEOUT = 1L
private const val CALL_TIMEOUT    = 1L

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.MINUTES)
            .readTimeout(READ_TIMEOUT, TimeUnit.MINUTES)
            .callTimeout(CALL_TIMEOUT, TimeUnit.MINUTES)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(AnimeApi::class.java)
    }

    // Interceptor
    // GsonConverterFactory
    // Cache
}
