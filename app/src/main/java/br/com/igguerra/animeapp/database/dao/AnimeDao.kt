package br.com.igguerra.animeapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import br.com.igguerra.animeapp.database.entity.AnimeDto

@Dao
interface AnimeDao {
    @Query("SELECT * from animes ORDER BY score ASC")
    fun getAll(): LiveData<List<AnimeDto>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(animeDto: AnimeDto)

    @Delete()
    suspend fun delete(animeDto: AnimeDto)

    @Query("DELETE FROM animes")
    suspend fun deleteAll()
}