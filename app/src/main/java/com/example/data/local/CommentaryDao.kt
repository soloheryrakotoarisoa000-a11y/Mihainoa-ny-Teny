package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.TheologicalCommentaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentaryDao {
    @Query("""
        SELECT * FROM theological_commentaries 
        WHERE bookNumber = :bookNumber AND chapter = :chapter 
        ORDER BY verseStart ASC
    """)
    fun getCommentariesForChapterFlow(bookNumber: Int, chapter: Int): Flow<List<TheologicalCommentaryEntity>>

    @Query("SELECT * FROM theological_commentaries ORDER BY id DESC")
    fun getAllCommentariesFlow(): Flow<List<TheologicalCommentaryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCommentaries(commentaries: List<TheologicalCommentaryEntity>)
}
