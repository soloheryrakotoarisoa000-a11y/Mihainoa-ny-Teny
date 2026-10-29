package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personal_notes")
data class PersonalNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val content: String,
    val bibleRef: String? = null,
    val publicationId: Long? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val colorHex: String = "#C59A3F"
)

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookNumber: Int,
    val chapter: Int,
    val verse: Int,
    val label: String,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val itemType: String, // "VERSE", "PUBLICATION", "EXEGESIS", "COMMENTARY"
    val referenceKey: String, // e.g. "VERSE_1_1_1" or "PUB_2"
    val title: String,
    val subtitle: String,
    val excerpt: String,
    val createdAt: Long = System.currentTimeMillis()
)
