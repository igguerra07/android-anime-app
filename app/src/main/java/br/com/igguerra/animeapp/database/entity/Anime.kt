package br.com.igguerra.animeapp.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
class Anime(
    @PrimaryKey
    val id: Int,
    val episodes: Int,
    val cover: String,
    val score: Double,
    val title: String,
    val type: String,
    val url: String
)