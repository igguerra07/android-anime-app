package br.com.igguerra.animeapp.database

import br.com.igguerra.animeapp.database.entity.Anime
import br.com.igguerra.animeapp.model.AnimeItem

object ItemAnimeMapper {
    fun itemToAnime(item: AnimeItem): Anime {
        return Anime(
            item.mal_id,
            item.episodes,
            item.image_url,
            item.score,
            item.title,
            item.type,
            item.url
        )
    }

    fun animeToItem(anime: Anime): AnimeItem {
        return AnimeItem(
            "",
            anime.episodes,
            anime.cover,
            anime.id,
            0,
            0,
            anime.score,
            "",
            anime.title,
            anime.type,
            anime.url
        )
    }
}