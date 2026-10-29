package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppBottomNavBar
import com.example.ui.components.AppTopBar
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminPortalScreen
import com.example.ui.screens.BibleCompareScreen
import com.example.ui.screens.BibleReaderScreen
import com.example.ui.screens.CommentariesScreen
import com.example.ui.screens.ExegesisScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PublicationDetailScreen
import com.example.ui.screens.PublicationsScreen
import com.example.ui.screens.UserStudyScreen
import com.example.ui.theme.MihainoaTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MihainoaTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp(viewModel: MainViewModel = viewModel()) {
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val translations by viewModel.translations.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()

    val publishedPubs by viewModel.publishedPublications.collectAsState()
    val adminPubs by viewModel.adminPublications.collectAsState()
    val latestBookmark by viewModel.latestBookmark.collectAsState()
    val allNotes by viewModel.allNotes.collectAsState()
    val allBookmarks by viewModel.allBookmarks.collectAsState()
    val allFavorites by viewModel.allFavorites.collectAsState()
    val allCommentaries by viewModel.allCommentaries.collectAsState()

    val fontSizeSp by viewModel.fontSizeSp.collectAsState()
    val lineSpacingMultiplier by viewModel.lineSpacingMultiplier.collectAsState()
    val surfaceMode by viewModel.readingSurfaceMode.collectAsState()
    val isAdminAuth by viewModel.isAdminAuthenticated.collectAsState()

    val feedbackMessage by viewModel.userFeedbackMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(feedbackMessage) {
        feedbackMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearFeedback()
        }
    }

    // Top Level Back Handler
    BackHandler(enabled = currentScreen !is Screen.Home) {
        viewModel.navigateBack()
    }

    Scaffold(
        topBar = {
            AppTopBar(
                translations = translations,
                currentLanguage = currentLanguage,
                onLanguageSelected = { viewModel.setLanguage(it) },
                onNavigateAdmin = { viewModel.navigateTo(Screen.AdminPortal) },
                onNavigateAbout = { viewModel.navigateTo(Screen.About) }
            )
        },
        bottomBar = {
            AppBottomNavBar(
                currentScreen = currentScreen,
                translations = translations,
                onNavigate = { viewModel.navigateTo(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (val screen = currentScreen) {
                is Screen.Home -> {
                    HomeScreen(
                        translations = translations,
                        publications = publishedPubs,
                        latestBookmark = latestBookmark,
                        isAdmin = isAdminAuth,
                        onNavigate = { viewModel.navigateTo(it) }
                    )
                }

                is Screen.PublicationsList -> {
                    PublicationsScreen(
                        translations = translations,
                        publications = publishedPubs,
                        initialFilterType = screen.filterType,
                        initialSearchQuery = screen.initialQuery,
                        onNavigateDetail = { viewModel.navigateTo(Screen.PublicationDetail(it)) }
                    )
                }

                is Screen.PublicationDetail -> {
                    val pub = publishedPubs.find { it.id == screen.pubId }
                        ?: adminPubs.find { it.id == screen.pubId }
                    val favKey = "PUB_${screen.pubId}"
                    val isFav = allFavorites.any { it.referenceKey == favKey }

                    PublicationDetailScreen(
                        publication = pub,
                        isFavorite = isFav,
                        translations = translations,
                        onBack = { viewModel.navigateBack() },
                        onToggleFavorite = { p ->
                            viewModel.toggleFavorite(
                                key = "PUB_${p.id}",
                                itemType = "PUBLICATION",
                                title = p.title,
                                subtitle = p.type,
                                excerpt = p.summary.ifBlank { p.fullText.take(150) }
                            )
                        }
                    )
                }

                is Screen.BibleReader -> {
                    BibleReaderScreen(
                        initialBookNumber = screen.bookNumber,
                        initialChapter = screen.chapter,
                        initialVerse = screen.verse,
                        initialVersionCode = screen.versionCode,
                        repository = viewModel.repository,
                        translations = translations,
                        currentLanguage = currentLanguage,
                        fontSizeSp = fontSizeSp,
                        lineSpacingMultiplier = lineSpacingMultiplier,
                        surfaceMode = surfaceMode,
                        onFontSizeChange = { viewModel.setFontSize(it) },
                        onLineSpacingChange = { viewModel.setLineSpacing(it) },
                        onSurfaceModeChange = { viewModel.setReadingSurface(it) },
                        onAddBookmark = { b, c, v, l -> viewModel.addBookmark(b, c, v, l) },
                        onAddNote = { t, c, r -> viewModel.saveNote(0L, t, c, r) },
                        onToggleFavorite = { k, t, title, sub, ex ->
                            viewModel.toggleFavorite(k, t, title, sub, ex)
                        },
                        onNavigateCompare = { b, c, v ->
                            viewModel.navigateTo(Screen.BibleCompare(b, c, v))
                        },
                        onNavigateExegesis = { b, c, v ->
                            viewModel.navigateTo(Screen.Exegesis(b, c, v))
                        },
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.BibleCompare -> {
                    BibleCompareScreen(
                        initialBookNumber = screen.bookNumber,
                        initialChapter = screen.chapter,
                        initialVerse = screen.verse,
                        repository = viewModel.repository,
                        translations = translations,
                        currentLanguage = currentLanguage,
                        onBack = { viewModel.navigateBack() },
                        onToggleFavorite = { k, t, title, sub, ex ->
                            viewModel.toggleFavorite(k, t, title, sub, ex)
                        }
                    )
                }

                is Screen.Exegesis -> {
                    ExegesisScreen(
                        initialBookNumber = screen.bookNumber,
                        initialChapter = screen.chapter,
                        initialVerse = screen.verse,
                        repository = viewModel.repository,
                        translations = translations,
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.Commentaries -> {
                    CommentariesScreen(
                        commentaries = allCommentaries,
                        translations = translations,
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.UserStudy -> {
                    UserStudyScreen(
                        initialTab = screen.initialTab,
                        notes = allNotes,
                        bookmarks = allBookmarks,
                        favorites = allFavorites,
                        translations = translations,
                        onNavigateBible = { b, c, v ->
                            viewModel.navigateTo(Screen.BibleReader(b, c, v))
                        },
                        onNavigatePublication = {
                            viewModel.navigateTo(Screen.PublicationDetail(it))
                        },
                        onSaveNote = { id, t, c, r ->
                            viewModel.saveNote(id, t, c, r)
                        },
                        onDeleteNote = { viewModel.deleteNote(it) },
                        onDeleteBookmark = { viewModel.deleteBookmark(it) },
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.AdminPortal -> {
                    AdminPortalScreen(
                        isAuthenticated = isAdminAuth,
                        adminPublications = adminPubs,
                        translations = translations,
                        onVerifyPin = { viewModel.verifyAdminPin(it) },
                        onSavePublication = { pub, onDone ->
                            viewModel.savePublication(pub, onDone)
                        },
                        onDeletePublication = { pub, onDone ->
                            viewModel.deletePublication(pub, onDone)
                        },
                        onExportBackup = { callback ->
                            viewModel.exportBackup(callback)
                        },
                        onImportBackup = { json, callback ->
                            viewModel.importBackup(json, callback)
                        },
                        onBack = { viewModel.navigateBack() }
                    )
                }

                is Screen.About -> {
                    AboutScreen(
                        repository = viewModel.repository,
                        translations = translations,
                        onBack = { viewModel.navigateBack() }
                    )
                }
            }
        }
    }
}
