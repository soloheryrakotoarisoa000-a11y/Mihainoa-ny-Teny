package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publications")
data class PublicationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val summary: String,
    val fullText: String,
    val originalLang: String, // "mg", "fr", "en"
    val author: String = "Solohery RAKOTOARISOA",
    val date: String,
    val bibleRefs: String,
    val type: String, // "SERMON", "CONFERENCE", "BIBLE_STUDY", "DOCTRINE", "TRAINING", "EXEGESIS", "HOMILETIC_PLAN"
    val categories: String,
    val practicalApplications: String,
    val bibliography: String,
    val audioFileName: String? = null,
    val isOfficial: Boolean = true,
    val isPublished: Boolean = true,
    val frenchTranslationTitle: String? = null,
    val frenchTranslationText: String? = null,
    val englishTranslationTitle: String? = null,
    val englishTranslationText: String? = null
)
