package br.com.igguerra.animeapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.igguerra.animeapp.database.dao.AnimeDao
import br.com.igguerra.animeapp.database.entity.AnimeDto

@Database(entities = [AnimeDto::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}
