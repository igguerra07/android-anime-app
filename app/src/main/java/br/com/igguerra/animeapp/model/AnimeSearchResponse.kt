package br.com.igguerra.animeapp.model

data class AnimeSearchResponse(
    val request_hash: String,
    val request_cached: Boolean,
    val request_cache_expiry: Int,
    val results: List<AnimeItem>
)