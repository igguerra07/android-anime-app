package br.com.igguerra.animeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.igguerra.animeapp.database.dao.AnimeDao
import br.com.igguerra.animeapp.database.entity.Anime

@Database(entities = [Anime::class], version = 1)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao

    companion object {
        private var database: AnimeDatabase? = null
        private const val DATABASE = "AnimeDB"
        fun getInstance(context: Context): AnimeDatabase {
            return database ?: createDatabase(context).also { database = it }
        }

        private fun createDatabase(context: Context): AnimeDatabase {
            return Room.databaseBuilder(context, AnimeDatabase::class.java, DATABASE)
                .allowMainThreadQueries()
                .build()
        }
    }
}
