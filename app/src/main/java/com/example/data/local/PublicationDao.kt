package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.PublicationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PublicationDao {
    @Query("SELECT * FROM publications WHERE isPublished = 1 ORDER BY id DESC")
    fun getAllPublishedFlow(): Flow<List<PublicationEntity>>

    @Query("SELECT * FROM publications ORDER BY id DESC")
    fun getAllAdminFlow(): Flow<List<PublicationEntity>>

    @Query("SELECT * FROM publications WHERE id = :id")
    suspend fun getById(id: Long): PublicationEntity?

    @Query("""
        SELECT * FROM publications 
        WHERE (isPublished = 1 OR :includeDrafts = 1)
        AND (
            title LIKE '%' || :query || '%' 
            OR summary LIKE '%' || :query || '%' 
            OR fullText LIKE '%' || :query || '%' 
            OR bibleRefs LIKE '%' || :query || '%' 
            OR categories LIKE '%' || :query || '%'
        )
        ORDER BY id DESC
    """)
    fun searchPublicationsFlow(query: String, includeDrafts: Boolean = false): Flow<List<PublicationEntity>>

    @Query("SELECT * FROM publications WHERE isPublished = 1 AND type = :type ORDER BY id DESC")
    fun getByTypeFlow(type: String): Flow<List<PublicationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(publication: PublicationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(publications: List<PublicationEntity>)

    @Update
    suspend fun update(publication: PublicationEntity)

    @Delete
    suspend fun delete(publication: PublicationEntity)

    @Query("DELETE FROM publications")
    suspend fun clearAll()
}
