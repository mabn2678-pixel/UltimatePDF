package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CachedAudioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cachedAudio: CachedAudio): Long

    @Query("SELECT * FROM cached_audios WHERE sourceUrl = :url LIMIT 1")
    suspend fun getBySourceUrl(url: String): CachedAudio?

    @Query("SELECT * FROM cached_audios WHERE savedAt < :cutoffTime")
    suspend fun getExpiredEntries(cutoffTime: Long): List<CachedAudio>

    @Query("DELETE FROM cached_audios WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM cached_audios ORDER BY savedAt DESC")
    fun getAll(): Flow<List<CachedAudio>>
}
