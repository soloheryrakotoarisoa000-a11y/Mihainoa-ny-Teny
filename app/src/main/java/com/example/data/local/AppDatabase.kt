package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import com.example.data.model.BookmarkEntity
import com.example.data.model.FavoriteEntity
import com.example.data.model.OriginalWordEntity
import com.example.data.model.PersonalNoteEntity
import com.example.data.model.PublicationEntity
import com.example.data.model.TheologicalCommentaryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        PublicationEntity::class,
        BibleVersionEntity::class,
        BibleBookEntity::class,
        BibleVerseEntity::class,
        OriginalWordEntity::class,
        TheologicalCommentaryEntity::class,
        PersonalNoteEntity::class,
        BookmarkEntity::class,
        FavoriteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun publicationDao(): PublicationDao
    abstract fun bibleDao(): BibleDao
    abstract fun exegesisDao(): ExegesisDao
    abstract fun commentaryDao(): CommentaryDao
    abstract fun userStudyDao(): UserStudyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "mihainoa_ny_teny_database"
                )
                    .createFromAsset("database/mihainoa_ny_teny_prepopulated.db")
                    .addCallback(DatabaseCallback())
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateInitialData(database)
                }
            }
        }

        private suspend fun populateInitialData(database: AppDatabase) {
            val bibleDao = database.bibleDao()
            val publicationDao = database.publicationDao()
            val exegesisDao = database.exegesisDao()
            val commentaryDao = database.commentaryDao()

            bibleDao.insertVersions(PrepopulatedData.versions)
            bibleDao.insertBooks(PrepopulatedData.books)
            bibleDao.insertVerses(PrepopulatedData.verses)
            exegesisDao.insertWords(PrepopulatedData.originalWords)
            commentaryDao.insertCommentaries(PrepopulatedData.commentaries)
            publicationDao.insertAll(PrepopulatedData.publications)
        }
    }
}
