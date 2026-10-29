package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bible_versions")
data class BibleVersionEntity(
    @PrimaryKey
    val code: String, // "MBP", "LSG1910", "BHS", "NA28", "DIEM"
    val name: String,
    val language: String, // "mg", "fr", "he", "grc", "en"
    val edition: String,
    val publisher: String,
    val source: String,
    val license: String,
    val isInstalled: Boolean = true,
    val isOriginalText: Boolean = false
)

@Entity(tableName = "bible_books")
data class BibleBookEntity(
    @PrimaryKey
    val bookNumber: Int, // 1 to 66
    val testament: String, // "OT", "NT"
    val nameMg: String,
    val nameFr: String,
    val nameEn: String,
    val abbrMg: String,
    val abbrFr: String,
    val abbrEn: String,
    val chapterCount: Int
)

@Entity(tableName = "bible_verses")
data class BibleVerseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookNumber: Int,
    val chapter: Int,
    val verse: Int,
    val versionCode: String, // "MBP", "LSG1910", "BHS", "NA28"
    val text: String
)

@Entity(tableName = "original_words")
data class OriginalWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookNumber: Int,
    val chapter: Int,
    val verse: Int,
    val wordIndex: Int,
    val originalText: String,
    val transliteration: String,
    val lemma: String,
    val strongNumber: String,
    val language: String, // "HEBREW" or "GREEK"
    val morphologyCode: String,
    val morphologyDesc: String,
    val definition: String,
    val theologicalNote: String,
    val isRtl: Boolean
)

@Entity(tableName = "theological_commentaries")
data class TheologicalCommentaryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookNumber: Int,
    val chapter: Int,
    val verseStart: Int,
    val verseEnd: Int,
    val passageTitle: String,
    val author: String,
    val workTitle: String,
    val license: String,
    val historicalContext: String,
    val literaryStructure: String,
    val verseExposition: String,
    val christocentricFocus: String,
    val homileticPoints: String,
    val studyQuestions: String
)
