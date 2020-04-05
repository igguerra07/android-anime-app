package br.com.igguerra.animeapp.database

import br.com.igguerra.animeapp.database.entity.AnimeDto
import br.com.igguerra.animeapp.model.Anime

object AnimeDataMapper {
    fun animeToDto(anime: Anime): AnimeDto {
        return AnimeDto(
            anime.mal_id,
            anime.episodes,
            anime.image_url,
            anime.score,
            anime.title,
            anime.type,
            anime.url
        )
    }

    fun dtoToAnime(animeDto: AnimeDto): Anime {
        return Anime(
            "",
            animeDto.episodes,
            animeDto.cover,
            animeDto.id,
            0,
            0,
            animeDto.score,
            "",
            animeDto.title,
            animeDto.type,
            animeDto.url
        )
    }
}