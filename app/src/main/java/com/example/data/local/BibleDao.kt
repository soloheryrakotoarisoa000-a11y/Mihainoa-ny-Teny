package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BibleDao {
    @Query("SELECT * FROM bible_versions ORDER BY isOriginalText ASC, code ASC")
    fun getAllVersionsFlow(): Flow<List<BibleVersionEntity>>

    @Query("SELECT * FROM bible_books ORDER BY bookNumber ASC")
    fun getAllBooksFlow(): Flow<List<BibleBookEntity>>

    @Query("SELECT * FROM bible_books WHERE bookNumber = :bookNumber LIMIT 1")
    suspend fun getBookByNumber(bookNumber: Int): BibleBookEntity?

    @Query("SELECT * FROM bible_verses WHERE bookNumber = :bookNumber AND chapter = :chapter AND versionCode = :versionCode ORDER BY verse ASC")
    fun getChapterVersesFlow(bookNumber: Int, chapter: Int, versionCode: String): Flow<List<BibleVerseEntity>>

    @Query("SELECT * FROM bible_verses WHERE bookNumber = :bookNumber AND chapter = :chapter AND verse = :verse AND versionCode = :versionCode LIMIT 1")
    suspend fun getVerse(bookNumber: Int, chapter: Int, verse: Int, versionCode: String): BibleVerseEntity?

    @Query("SELECT * FROM bible_verses WHERE bookNumber = :bookNumber AND chapter = :chapter AND verse = :verse")
    fun getVerseAllVersionsFlow(bookNumber: Int, chapter: Int, verse: Int): Flow<List<BibleVerseEntity>>

    @Query("""
        SELECT * FROM bible_verses 
        WHERE versionCode = :versionCode AND text LIKE '%' || :query || '%' 
        LIMIT 50
    """)
    fun searchVersesFlow(versionCode: String, query: String): Flow<List<BibleVerseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVersions(versions: List<BibleVersionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<BibleBookEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVerses(verses: List<BibleVerseEntity>)
}
