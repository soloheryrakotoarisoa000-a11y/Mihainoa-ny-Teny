package com.example.data.repository

import android.content.Context
import com.example.data.local.AppDatabase
import com.example.data.local.PrepopulatedData
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import com.example.data.model.BookmarkEntity
import com.example.data.model.FavoriteEntity
import com.example.data.model.OriginalWordEntity
import com.example.data.model.PersonalNoteEntity
import com.example.data.model.PublicationEntity
import com.example.data.model.TheologicalCommentaryEntity
import com.example.ui.i18n.AppLanguage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

class AppRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val pubDao = db.publicationDao()
    private val bibleDao = db.bibleDao()
    private val exegesisDao = db.exegesisDao()
    private val commentaryDao = db.commentaryDao()
    private val userStudyDao = db.userStudyDao()

    suspend fun ensureDataSeeded() = withContext(Dispatchers.IO) {
        val currentVersions = bibleDao.getAllVersionsFlow().firstOrNull() ?: emptyList()
        if (currentVersions.size < PrepopulatedData.versions.size) {
            bibleDao.insertVersions(PrepopulatedData.versions)
            bibleDao.insertBooks(PrepopulatedData.books)
            bibleDao.insertVerses(PrepopulatedData.verses)
            exegesisDao.insertWords(PrepopulatedData.originalWords)
            commentaryDao.insertCommentaries(PrepopulatedData.commentaries)
            val currentPubs = pubDao.getAllAdminFlow().firstOrNull() ?: emptyList()
            if (currentPubs.isEmpty()) {
                pubDao.insertAll(PrepopulatedData.publications)
            }
        }
    }

    // Publications
    val publishedPublications: Flow<List<PublicationEntity>> = pubDao.getAllPublishedFlow()
    val allAdminPublications: Flow<List<PublicationEntity>> = pubDao.getAllAdminFlow()

    fun searchPublications(query: String, includeDrafts: Boolean = false): Flow<List<PublicationEntity>> =
        pubDao.searchPublicationsFlow(query, includeDrafts)

    fun getPublicationsByType(type: String): Flow<List<PublicationEntity>> =
        pubDao.getByTypeFlow(type)

    suspend fun getPublicationById(id: Long): PublicationEntity? = withContext(Dispatchers.IO) {
        pubDao.getById(id)
    }

    suspend fun savePublication(publication: PublicationEntity): Long = withContext(Dispatchers.IO) {
        if (publication.id == 0L) {
            pubDao.insert(publication)
        } else {
            pubDao.update(publication)
            publication.id
        }
    }

    suspend fun deletePublication(publication: PublicationEntity) = withContext(Dispatchers.IO) {
        pubDao.delete(publication)
    }

    // Bible
    val bibleVersions: Flow<List<BibleVersionEntity>> = bibleDao.getAllVersionsFlow()
    val bibleBooks: Flow<List<BibleBookEntity>> = bibleDao.getAllBooksFlow()

    suspend fun getBook(bookNumber: Int): BibleBookEntity? = withContext(Dispatchers.IO) {
        bibleDao.getBookByNumber(bookNumber)
    }

    fun getChapterVerses(bookNumber: Int, chapter: Int, versionCode: String): Flow<List<BibleVerseEntity>> =
        bibleDao.getChapterVersesFlow(bookNumber, chapter, versionCode)

    suspend fun getSingleVerse(bookNumber: Int, chapter: Int, verse: Int, versionCode: String): BibleVerseEntity? =
        withContext(Dispatchers.IO) {
            bibleDao.getVerse(bookNumber, chapter, verse, versionCode)
        }

    fun getVerseAcrossVersions(bookNumber: Int, chapter: Int, verse: Int): Flow<List<BibleVerseEntity>> =
        bibleDao.getVerseAllVersionsFlow(bookNumber, chapter, verse)

    fun searchVerses(versionCode: String, query: String): Flow<List<BibleVerseEntity>> =
        bibleDao.searchVersesFlow(versionCode, query)

    // Exegesis
    fun getOriginalWordsForVerse(bookNumber: Int, chapter: Int, verse: Int): Flow<List<OriginalWordEntity>> =
        exegesisDao.getWordsForVerseFlow(bookNumber, chapter, verse)

    fun getWordsByStrong(strongNumber: String): Flow<List<OriginalWordEntity>> =
        exegesisDao.getWordsByStrongFlow(strongNumber)

    fun getAllOriginalWords(language: String): Flow<List<OriginalWordEntity>> =
        exegesisDao.getAllWordsByLanguageFlow(language)

    // Commentaries
    fun getCommentariesForChapter(bookNumber: Int, chapter: Int): Flow<List<TheologicalCommentaryEntity>> =
        commentaryDao.getCommentariesForChapterFlow(bookNumber, chapter)

    val allCommentaries: Flow<List<TheologicalCommentaryEntity>> = commentaryDao.getAllCommentariesFlow()

    // Notes
    val allNotes: Flow<List<PersonalNoteEntity>> = userStudyDao.getAllNotesFlow()

    suspend fun saveNote(note: PersonalNoteEntity): Long = withContext(Dispatchers.IO) {
        if (note.id == 0L) {
            userStudyDao.insertNote(note)
        } else {
            userStudyDao.updateNote(note)
            note.id
        }
    }

    suspend fun deleteNote(note: PersonalNoteEntity) = withContext(Dispatchers.IO) {
        userStudyDao.deleteNote(note)
    }

    // Bookmarks
    val allBookmarks: Flow<List<BookmarkEntity>> = userStudyDao.getAllBookmarksFlow()
    val latestBookmark: Flow<BookmarkEntity?> = userStudyDao.getLatestBookmarkFlow()

    suspend fun addBookmark(bookNumber: Int, chapter: Int, verse: Int, label: String): Long = withContext(Dispatchers.IO) {
        userStudyDao.insertBookmark(
            BookmarkEntity(
                bookNumber = bookNumber,
                chapter = chapter,
                verse = verse,
                label = label
            )
        )
    }

    suspend fun deleteBookmark(bookmark: BookmarkEntity) = withContext(Dispatchers.IO) {
        userStudyDao.deleteBookmark(bookmark)
    }

    // Favorites
    val allFavorites: Flow<List<FavoriteEntity>> = userStudyDao.getAllFavoritesFlow()

    fun isFavorite(key: String): Flow<FavoriteEntity?> = userStudyDao.getFavoriteByKeyFlow(key)

    suspend fun toggleFavorite(key: String, itemType: String, title: String, subtitle: String, excerpt: String): Boolean =
        withContext(Dispatchers.IO) {
            val existing = userStudyDao.getFavoriteByKey(key)
            if (existing != null) {
                userStudyDao.deleteFavoriteByKey(key)
                false
            } else {
                userStudyDao.insertFavorite(
                    FavoriteEntity(
                        itemType = itemType,
                        referenceKey = key,
                        title = title,
                        subtitle = subtitle,
                        excerpt = excerpt
                    )
                )
                true
            }
        }

    // Backup & Export JSON
    suspend fun exportBackupJson(): String = withContext(Dispatchers.IO) {
        val root = JSONObject()
        root.put("app", "Mihainoa ny Teny")
        root.put("author", "Solohery RAKOTOARISOA")
        root.put("exportTimestamp", System.currentTimeMillis())

        val pubs = pubDao.getAllAdminFlow().first()
        val pubsArray = JSONArray()
        pubs.forEach { p ->
            val pObj = JSONObject()
            pObj.put("title", p.title)
            pObj.put("summary", p.summary)
            pObj.put("fullText", p.fullText)
            pObj.put("originalLang", p.originalLang)
            pObj.put("author", p.author)
            pObj.put("date", p.date)
            pObj.put("bibleRefs", p.bibleRefs)
            pObj.put("type", p.type)
            pObj.put("categories", p.categories)
            pObj.put("practicalApplications", p.practicalApplications)
            pObj.put("bibliography", p.bibliography)
            pObj.put("isPublished", p.isPublished)
            pubsArray.put(pObj)
        }
        root.put("publications", pubsArray)

        val notes = userStudyDao.getAllNotesFlow().first()
        val notesArray = JSONArray()
        notes.forEach { n ->
            val nObj = JSONObject()
            nObj.put("title", n.title)
            nObj.put("content", n.content)
            nObj.put("bibleRef", n.bibleRef)
            nObj.put("createdAt", n.createdAt)
            notesArray.put(nObj)
        }
        root.put("personalNotes", notesArray)

        root.toString(2)
    }

    suspend fun importBackupJson(jsonString: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)
            if (root.has("publications")) {
                val pubsArray = root.getJSONArray("publications")
                for (i in 0 until pubsArray.length()) {
                    val pObj = pubsArray.getJSONObject(i)
                    val p = PublicationEntity(
                        title = pObj.optString("title", "Sans titre"),
                        summary = pObj.optString("summary", ""),
                        fullText = pObj.optString("fullText", ""),
                        originalLang = pObj.optString("originalLang", "mg"),
                        author = pObj.optString("author", "Solohery RAKOTOARISOA"),
                        date = pObj.optString("date", "2026-10-29"),
                        bibleRefs = pObj.optString("bibleRefs", ""),
                        type = pObj.optString("type", "SERMON"),
                        categories = pObj.optString("categories", ""),
                        practicalApplications = pObj.optString("practicalApplications", ""),
                        bibliography = pObj.optString("bibliography", ""),
                        isPublished = pObj.optBoolean("isPublished", true)
                    )
                    pubDao.insert(p)
                }
            }
            if (root.has("personalNotes")) {
                val notesArray = root.getJSONArray("personalNotes")
                for (i in 0 until notesArray.length()) {
                    val nObj = notesArray.getJSONObject(i)
                    val n = PersonalNoteEntity(
                        title = nObj.optString("title", "Note"),
                        content = nObj.optString("content", ""),
                        bibleRef = if (nObj.has("bibleRef")) nObj.optString("bibleRef") else null,
                        createdAt = nObj.optLong("createdAt", System.currentTimeMillis())
                    )
                    userStudyDao.insertNote(n)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
