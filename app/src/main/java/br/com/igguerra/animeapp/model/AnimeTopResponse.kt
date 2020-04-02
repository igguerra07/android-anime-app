package br.com.igguerra.animeapp.model

data class AnimeTopResponse(
    val request_hash: String,
    val request_cached: Boolean,
    val request_cache_expiry: Int,
    val top: List<AnimeTop>
)