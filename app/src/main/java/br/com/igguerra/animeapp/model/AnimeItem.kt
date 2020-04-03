package br.com.igguerra.animeapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnimeItem(
    val end_date: String,
    val episodes: Int,
    val image_url: String,
    val mal_id: Int,
    val members: Int,
    val rank: Int? = 0,
    val score: Double,
    val start_date: String,
    val title: String,
    val type: String,
    val url: String
) : Parcelable