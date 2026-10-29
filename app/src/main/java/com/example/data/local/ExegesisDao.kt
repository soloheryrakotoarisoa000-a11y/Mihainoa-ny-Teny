package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.OriginalWordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExegesisDao {
    @Query("""
        SELECT * FROM original_words 
        WHERE bookNumber = :bookNumber AND chapter = :chapter AND verse = :verse 
        ORDER BY wordIndex ASC
    """)
    fun getWordsForVerseFlow(bookNumber: Int, chapter: Int, verse: Int): Flow<List<OriginalWordEntity>>

    @Query("SELECT * FROM original_words WHERE strongNumber = :strongNumber LIMIT 10")
    fun getWordsByStrongFlow(strongNumber: String): Flow<List<OriginalWordEntity>>

    @Query("SELECT * FROM original_words WHERE language = :language ORDER BY id ASC")
    fun getAllWordsByLanguageFlow(language: String): Flow<List<OriginalWordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<OriginalWordEntity>)
}
