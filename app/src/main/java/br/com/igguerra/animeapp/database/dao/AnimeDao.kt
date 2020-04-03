package br.com.igguerra.animeapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.igguerra.animeapp.database.entity.Anime

@Dao
interface AnimeDao {

    @Query("SELECT * from animes ORDER BY score ASC")
    fun getAll(): LiveData<List<Anime>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(anime: Anime)

    @Delete()
    suspend fun delete(anime: Anime)

    @Query("DELETE FROM animes")
    suspend fun deleteAll()
}