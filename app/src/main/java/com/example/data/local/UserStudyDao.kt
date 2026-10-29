package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.BookmarkEntity
import com.example.data.model.FavoriteEntity
import com.example.data.model.PersonalNoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserStudyDao {
    // Notes
    @Query("SELECT * FROM personal_notes ORDER BY createdAt DESC")
    fun getAllNotesFlow(): Flow<List<PersonalNoteEntity>>

    @Query("SELECT * FROM personal_notes WHERE id = :id")
    suspend fun getNoteById(id: Long): PersonalNoteEntity?

    @Query("SELECT * FROM personal_notes WHERE bibleRef = :bibleRef")
    fun getNotesForBibleRefFlow(bibleRef: String): Flow<List<PersonalNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: PersonalNoteEntity): Long

    @Update
    suspend fun updateNote(note: PersonalNoteEntity)

    @Delete
    suspend fun deleteNote(note: PersonalNoteEntity)

    // Bookmarks
    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC")
    fun getAllBookmarksFlow(): Flow<List<BookmarkEntity>>

    @Query("SELECT * FROM bookmarks ORDER BY createdAt DESC LIMIT 1")
    fun getLatestBookmarkFlow(): Flow<BookmarkEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity): Long

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarkEntity)

    // Favorites
    @Query("SELECT * FROM favorites ORDER BY createdAt DESC")
    fun getAllFavoritesFlow(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE referenceKey = :key LIMIT 1")
    fun getFavoriteByKeyFlow(key: String): Flow<FavoriteEntity?>

    @Query("SELECT * FROM favorites WHERE referenceKey = :key LIMIT 1")
    suspend fun getFavoriteByKey(key: String): FavoriteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity): Long

    @Query("DELETE FROM favorites WHERE referenceKey = :key")
    suspend fun deleteFavoriteByKey(key: String)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}
