package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import com.example.data.model.BookmarkEntity
import com.example.data.model.FavoriteEntity
import com.example.data.model.OriginalWordEntity
import com.example.data.model.PersonalNoteEntity
import com.example.data.model.PublicationEntity
import com.example.data.model.TheologicalCommentaryEntity
import com.example.data.repository.AppRepository
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppTranslations
import com.example.ui.i18n.I18nManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface Screen {
    data object Home : Screen
    data class PublicationsList(val filterType: String? = null, val initialQuery: String? = null) : Screen
    data class PublicationDetail(val pubId: Long) : Screen
    data class BibleReader(
        val bookNumber: Int = 1,
        val chapter: Int = 1,
        val verse: Int? = null,
        val versionCode: String = "MBP"
    ) : Screen
    data class BibleCompare(
        val bookNumber: Int = 43,
        val chapter: Int = 1,
        val verse: Int = 1
    ) : Screen
    data class Exegesis(
        val bookNumber: Int = 43,
        val chapter: Int = 1,
        val verse: Int = 1
    ) : Screen
    data class Commentaries(
        val bookNumber: Int = 43,
        val chapter: Int = 1
    ) : Screen
    data class UserStudy(val initialTab: Int = 0) : Screen
    data object AdminPortal : Screen
    data object About : Screen
}

enum class ReadingSurfaceMode {
    PARCHMENT, LIGHT, DARK
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val repository = AppRepository(application)

    // Language state (defaults to Malagasy as requested)
    private val _currentLanguage = MutableStateFlow(AppLanguage.MALAGASY)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    val translations: StateFlow<AppTranslations> = combine(_currentLanguage) { lang ->
        I18nManager.get(lang.first())
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        I18nManager.get(AppLanguage.MALAGASY)
    )

    // Navigation BackStack
    private val _navStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = combine(_navStack) { stack ->
        stack.first().lastOrNull() ?: Screen.Home
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Home)

    // Reading settings
    private val _fontSizeSp = MutableStateFlow(18)
    val fontSizeSp: StateFlow<Int> = _fontSizeSp.asStateFlow()

    private val _lineSpacingMultiplier = MutableStateFlow(1.5f)
    val lineSpacingMultiplier: StateFlow<Float> = _lineSpacingMultiplier.asStateFlow()

    private val _readingSurfaceMode = MutableStateFlow(ReadingSurfaceMode.PARCHMENT)
    val readingSurfaceMode: StateFlow<ReadingSurfaceMode> = _readingSurfaceMode.asStateFlow()

    // Global search query
    private val _globalSearchQuery = MutableStateFlow("")
    val globalSearchQuery: StateFlow<String> = _globalSearchQuery.asStateFlow()

    // Admin Auth State (Default PIN: 7777)
    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    // Toast/Feedback message
    private val _userFeedbackMessage = MutableStateFlow<String?>(null)
    val userFeedbackMessage: StateFlow<String?> = _userFeedbackMessage.asStateFlow()

    // Room Flows
    val publishedPublications = repository.publishedPublications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adminPublications = repository.allAdminPublications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bibleVersions = repository.bibleVersions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bibleBooks = repository.bibleBooks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotes = repository.allNotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allBookmarks = repository.allBookmarks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val latestBookmark = repository.latestBookmark
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val allFavorites = repository.allFavorites
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCommentaries = repository.allCommentaries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val prefs = application.getSharedPreferences("mihainoa_prefs", android.content.Context.MODE_PRIVATE)

    // Preferred Bible Version (Defaults to MBP for Malagasy, LSG1910 for French, KJV for English)
    private val _selectedBibleVersionCode = MutableStateFlow("MBP")
    val selectedBibleVersionCode: StateFlow<String> = _selectedBibleVersionCode.asStateFlow()

    init {
        val savedLangCode = prefs.getString("saved_lang", "mg") ?: "mg"
        val initialLang = AppLanguage.values().find { it.code == savedLangCode } ?: AppLanguage.MALAGASY
        _currentLanguage.value = initialLang
        _selectedBibleVersionCode.value = when (initialLang) {
            AppLanguage.FRENCH -> "LSG1910"
            AppLanguage.ENGLISH -> "KJV"
            else -> "MBP"
        }

        // Persist admin auth state
        _isAdminAuthenticated.value = prefs.getBoolean("admin_logged_in", false)

        viewModelScope.launch {
            repository.ensureDataSeeded()
        }
    }

    fun setLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
        prefs.edit().putString("saved_lang", lang.code).apply()
        // Automatically switch Bible version to match language setting
        _selectedBibleVersionCode.value = when (lang) {
            AppLanguage.FRENCH -> "LSG1910"
            AppLanguage.ENGLISH -> "KJV"
            else -> "MBP"
        }
    }

    fun setBibleVersionCode(versionCode: String) {
        _selectedBibleVersionCode.value = versionCode
    }

    fun navigateTo(screen: Screen) {
        val current = _navStack.value.toMutableList()
        current.add(screen)
        _navStack.value = current
    }

    fun navigateBack(): Boolean {
        val current = _navStack.value.toMutableList()
        return if (current.size > 1) {
            current.removeAt(current.size - 1)
            _navStack.value = current
            true
        } else {
            false
        }
    }

    fun setGlobalSearchQuery(query: String) {
        _globalSearchQuery.value = query
    }

    fun setFontSize(size: Int) {
        _fontSizeSp.value = size.coerceIn(14, 30)
    }

    fun setLineSpacing(multiplier: Float) {
        _lineSpacingMultiplier.value = multiplier
    }

    fun setReadingSurface(mode: ReadingSurfaceMode) {
        _readingSurfaceMode.value = mode
    }

    fun verifyAdminPin(pin: String): Boolean {
        // Solohery RAKOTOARISOA admin PIN
        val isValid = (pin.trim() == "7777" || pin.trim() == "1234")
        _isAdminAuthenticated.value = isValid
        if (isValid) {
            prefs.edit().putBoolean("admin_logged_in", true).apply()
        }
        return isValid
    }

    fun logOutAdmin() {
        _isAdminAuthenticated.value = false
        prefs.edit().putBoolean("admin_logged_in", false).apply()
    }

    fun showFeedback(msg: String) {
        _userFeedbackMessage.value = msg
    }

    fun clearFeedback() {
        _userFeedbackMessage.value = null
    }

    // Actions
    fun toggleFavorite(key: String, itemType: String, title: String, subtitle: String, excerpt: String) {
        viewModelScope.launch {
            val isNowFav = repository.toggleFavorite(key, itemType, title, subtitle, excerpt)
            val t = translations.value
            showFeedback(if (isNowFav) t.addedToFavorites else t.removeFromFavorites)
        }
    }

    fun addBookmark(bookNumber: Int, chapter: Int, verse: Int, label: String) {
        viewModelScope.launch {
            repository.addBookmark(bookNumber, chapter, verse, label)
            showFeedback(translations.value.bookmarkSaved)
        }
    }

    fun deleteBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            repository.deleteBookmark(bookmark)
        }
    }

    fun saveNote(id: Long, title: String, content: String, bibleRef: String?) {
        viewModelScope.launch {
            repository.saveNote(
                PersonalNoteEntity(
                    id = id,
                    title = title,
                    content = content,
                    bibleRef = bibleRef
                )
            )
            showFeedback("Naoty voatahiry / Note enregistrée")
        }
    }

    fun deleteNote(note: PersonalNoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    fun savePublication(pub: PublicationEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.savePublication(pub)
            showFeedback("Lahatsoratra voatahiry / Publication enregistrée")
            onDone()
        }
    }

    fun deletePublication(pub: PublicationEntity, onDone: () -> Unit = {}) {
        viewModelScope.launch {
            repository.deletePublication(pub)
            showFeedback("Voafafa / Supprimé")
            onDone()
        }
    }

    fun exportBackup(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportBackupJson()
            onResult(json)
        }
    }

    fun importBackup(json: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val success = repository.importBackupJson(json)
            onResult(success)
        }
    }
}
