package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleBookEntity
import com.example.data.model.BibleVerseEntity
import com.example.data.model.BibleVersionEntity
import com.example.data.repository.AppRepository
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.DarkBackgroundNight
import com.example.ui.theme.DarkSurfaceNight
import com.example.ui.theme.DarkTextHighContrast
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.IvoryBackgroundLight
import com.example.ui.theme.ParchmentReadingSurface
import com.example.ui.theme.ParchmentReadingText
import com.example.ui.viewmodel.ReadingSurfaceMode
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BibleReaderScreen(
    initialBookNumber: Int,
    initialChapter: Int,
    initialVerse: Int?,
    initialVersionCode: String,
    repository: AppRepository,
    translations: AppTranslations,
    currentLanguage: AppLanguage,
    fontSizeSp: Int,
    lineSpacingMultiplier: Float,
    surfaceMode: ReadingSurfaceMode,
    onFontSizeChange: (Int) -> Unit,
    onLineSpacingChange: (Float) -> Unit,
    onSurfaceModeChange: (ReadingSurfaceMode) -> Unit,
    onAddBookmark: (Int, Int, Int, String) -> Unit,
    onAddNote: (String, String, String) -> Unit,
    onToggleFavorite: (String, String, String, String, String) -> Unit,
    onNavigateCompare: (Int, Int, Int) -> Unit,
    onNavigateExegesis: (Int, Int, Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var selectedBookNumber by remember { mutableIntStateOf(initialBookNumber) }
    var selectedChapter by remember { mutableIntStateOf(initialChapter) }
    var selectedVersionCode by remember { mutableStateOf(initialVersionCode) }

    // Dialogs & Sheets
    var showBookPicker by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }
    var selectedVerseForAction by remember { mutableStateOf<BibleVerseEntity?>(null) }
    var showAddNoteDialog by remember { mutableStateOf(false) }
    var noteInputText by remember { mutableStateOf("") }
    var noteTitleText by remember { mutableStateOf("") }

    val books by repository.bibleBooks.collectAsState(initial = emptyList())
    val versions by repository.bibleVersions.collectAsState(initial = emptyList())
    val currentBook = books.find { it.bookNumber == selectedBookNumber } ?: books.firstOrNull()

    val currentBookName = when (currentLanguage) {
        AppLanguage.FRENCH -> currentBook?.nameFr ?: "Genèse"
        AppLanguage.ENGLISH -> currentBook?.nameEn ?: "Genesis"
        else -> currentBook?.nameMg ?: "Genesisy"
    }

    val verses by repository.getChapterVerses(selectedBookNumber, selectedChapter, selectedVersionCode)
        .collectAsState(initial = emptyList())

    // Determine Surface Colors based on Reading Mode
    val (surfaceBgColor, surfaceTextColor) = when (surfaceMode) {
        ReadingSurfaceMode.PARCHMENT -> ParchmentReadingSurface to ParchmentReadingText
        ReadingSurfaceMode.DARK -> DarkBackgroundNight to DarkTextHighContrast
        ReadingSurfaceMode.LIGHT -> IvoryBackgroundLight to Color(0xFF141916)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surfaceBgColor)
            .testTag("bible_reader_screen")
    ) {
        // Bible Reader Top Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    // Book & Chapter Picker Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f))
                            .clickable { showBookPicker = true }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("bible_book_picker_button"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$currentBookName $selectedChapter",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Version Switcher
                    var versionMenuExpanded by remember { mutableStateOf(false) }
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { versionMenuExpanded = true }
                                .padding(horizontal = 8.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = selectedVersionCode,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        DropdownMenu(
                            expanded = versionMenuExpanded,
                            onDismissRequest = { versionMenuExpanded = false }
                        ) {
                            versions.forEach { v ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(
                                                text = "${v.code} - ${v.name}",
                                                fontWeight = if (v.code == selectedVersionCode) FontWeight.Bold else FontWeight.Normal,
                                                color = if (v.code == selectedVersionCode) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = if (v.isInstalled) translations.versionStatusInstalled else translations.versionStatusPending,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (v.isInstalled) Color(0xFF2E7D32) else Color(0xFFE65100)
                                            )
                                        }
                                    },
                                    onClick = {
                                        if (v.isInstalled) {
                                            selectedVersionCode = v.code
                                        }
                                        versionMenuExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    // Copy Entire Chapter Button
                    IconButton(
                        onClick = {
                            if (verses.isNotEmpty()) {
                                val fullChapterText = buildString {
                                    appendLine("$currentBookName $selectedChapter ($selectedVersionCode)")
                                    appendLine()
                                    verses.forEach { v ->
                                        appendLine("${v.verse}. ${v.text}")
                                    }
                                }
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("Chapter Text", fullChapterText)
                                clipboard.setPrimaryClip(clip)
                                android.widget.Toast.makeText(context, translations.textCopied, android.widget.Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.testTag("bible_copy_chapter_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = translations.copyDirect,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    // Reading Appearance Settings Button
                    IconButton(
                        onClick = { showSettingsSheet = true },
                        modifier = Modifier.testTag("bible_reading_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = translations.readingMode,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                // Chapter Bar Scroll
                val totalChapters = currentBook?.chapterCount ?: 1
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((1..totalChapters).toList()) { ch ->
                        val isSelected = ch == selectedChapter
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                                .clickable { selectedChapter = ch },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$ch",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }

        // Verses List
        if (verses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Ity toko ity ($currentBookName $selectedChapter) dia mbola ampidirina anaty tahiry.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Andramo ohatra : Genesisy 1, Salamo 23, Jaona 1, Romana 8.",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        } else {
            androidx.compose.foundation.text.selection.SelectionContainer {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(verses) { verseItem ->
                        val isHebrew = selectedVersionCode == "BHS"
                        val verseRefLabel = "$currentBookName ${verseItem.chapter}:${verseItem.verse}"

                        Card(
                            onClick = { selectedVerseForAction = verseItem },
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "${verseItem.verse}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldAccent
                                    ),
                                    modifier = Modifier.width(28.dp)
                                )

                                Text(
                                    text = verseItem.text,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = fontSizeSp.sp,
                                        lineHeight = (fontSizeSp * lineSpacingMultiplier).sp
                                    ),
                                    color = surfaceTextColor,
                                    textAlign = if (isHebrew) TextAlign.Right else TextAlign.Left,
                                    modifier = Modifier.weight(1f)
                                )

                                // Direct 1-tap copy button on each verse
                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        val clip = ClipData.newPlainText("Bible Verse", "$verseRefLabel (${verseItem.versionCode})\n${verseItem.text}")
                                        clipboard.setPrimaryClip(clip)
                                        android.widget.Toast.makeText(context, translations.verseCopiedDirect, android.widget.Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = translations.copyVerse,
                                        tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal Sheet: Verse Quick Actions
    if (selectedVerseForAction != null) {
        val verse = selectedVerseForAction!!
        val verseRefLabel = "$currentBookName ${verse.chapter}:${verse.verse}"

        ModalBottomSheet(
            onDismissRequest = { selectedVerseForAction = null },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = verseRefLabel,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "« ${verse.text} »",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = androidx.compose.ui.text.font.FontStyle.Italic),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Actions Grid / Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Copy
                    IconButton(onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Verse", "$verseRefLabel (${verse.versionCode})\n${verse.text}")
                        clipboard.setPrimaryClip(clip)
                        selectedVerseForAction = null
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ContentCopy, contentDescription = translations.copyVerse, tint = MaterialTheme.colorScheme.primary)
                            Text(translations.copyVerse.split(" ").first(), style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    // Bookmark
                    IconButton(onClick = {
                        onAddBookmark(verse.bookNumber, verse.chapter, verse.verse, verseRefLabel)
                        selectedVerseForAction = null
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Bookmark, contentDescription = translations.markBookmark, tint = GoldAccent)
                            Text(translations.markBookmark.split(" ").first(), style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    // Note
                    IconButton(onClick = {
                        noteTitleText = "Fanamarihana momba ny $verseRefLabel"
                        noteInputText = ""
                        showAddNoteDialog = true
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Edit, contentDescription = translations.addVerseNote, tint = MaterialTheme.colorScheme.primary)
                            Text(translations.addVerseNote.split(" ").first(), style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    // Favorite
                    IconButton(onClick = {
                        onToggleFavorite(
                            "VERSE_${verse.bookNumber}_${verse.chapter}_${verse.verse}",
                            "VERSE",
                            verseRefLabel,
                            verse.versionCode,
                            verse.text
                        )
                        selectedVerseForAction = null
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Favorite, contentDescription = translations.addedToFavorites, tint = Color.Red)
                            Text("Favoris", style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    // Compare
                    IconButton(onClick = {
                        val b = verse.bookNumber
                        val c = verse.chapter
                        val v = verse.verse
                        selectedVerseForAction = null
                        onNavigateCompare(b, c, v)
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.CompareArrows, contentDescription = translations.compareVersions, tint = MaterialTheme.colorScheme.secondary)
                            Text(translations.compareTitle.split(" ").first(), style = MaterialTheme.typography.labelSmall)
                        }
                    }

                    // Exegesis
                    IconButton(onClick = {
                        val b = verse.bookNumber
                        val c = verse.chapter
                        val v = verse.verse
                        selectedVerseForAction = null
                        onNavigateExegesis(b, c, v)
                    }) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Psychology, contentDescription = translations.viewExegesisForVerse, tint = MaterialTheme.colorScheme.primary)
                            Text("Egzeseze", style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Settings BottomSheet
    if (showSettingsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSettingsSheet = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = translations.readingMode,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Color Themes
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FilterChip(
                        selected = surfaceMode == ReadingSurfaceMode.PARCHMENT,
                        onClick = { onSurfaceModeChange(ReadingSurfaceMode.PARCHMENT) },
                        label = { Text(translations.modeParchment) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = surfaceMode == ReadingSurfaceMode.LIGHT,
                        onClick = { onSurfaceModeChange(ReadingSurfaceMode.LIGHT) },
                        label = { Text(translations.modeLight) },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = surfaceMode == ReadingSurfaceMode.DARK,
                        onClick = { onSurfaceModeChange(ReadingSurfaceMode.DARK) },
                        label = { Text(translations.modeDark) },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Font Size Slider
                Text(
                    text = "${translations.textSize} : ${fontSizeSp}sp",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Slider(
                    value = fontSizeSp.toFloat(),
                    onValueChange = { onFontSizeChange(it.toInt()) },
                    valueRange = 14f..28f,
                    steps = 6
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Line Spacing
                Text(
                    text = "${translations.lineSpacing} : ${(lineSpacingMultiplier * 100).toInt()}%",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Slider(
                    value = lineSpacingMultiplier,
                    onValueChange = { onLineSpacingChange(it) },
                    valueRange = 1.2f..2.0f,
                    steps = 4
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Book Picker Bottom Sheet
    if (showBookPicker) {
        var selectedTestamentTab by remember { mutableIntStateOf(0) }
        val testamentFilter = if (selectedTestamentTab == 0) "OT" else "NT"
        val displayedBooks = books.filter { it.testament == testamentFilter }

        ModalBottomSheet(
            onDismissRequest = { showBookPicker = false },
            sheetState = rememberModalBottomSheetState()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                TabRow(selectedTabIndex = selectedTestamentTab) {
                    Tab(
                        selected = selectedTestamentTab == 0,
                        onClick = { selectedTestamentTab = 0 },
                        text = { Text(translations.oldTestament) }
                    )
                    Tab(
                        selected = selectedTestamentTab == 1,
                        onClick = { selectedTestamentTab = 1 },
                        text = { Text(translations.newTestament) }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    modifier = Modifier.height(350.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(displayedBooks) { b ->
                        val bName = when (currentLanguage) {
                            AppLanguage.FRENCH -> b.nameFr
                            AppLanguage.ENGLISH -> b.nameEn
                            else -> b.nameMg
                        }
                        val isCurr = b.bookNumber == selectedBookNumber
                        Surface(
                            onClick = {
                                selectedBookNumber = b.bookNumber
                                selectedChapter = 1
                                showBookPicker = false
                            },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isCurr) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = bName,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isCurr) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isCurr) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                )
                                Text(
                                    text = "${b.chapterCount} ${translations.chapter.lowercase()}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Note Dialog
    if (showAddNoteDialog && selectedVerseForAction != null) {
        val v = selectedVerseForAction!!
        val verseRefLabel = "$currentBookName ${v.chapter}:${v.verse}"

        AlertDialog(
            onDismissRequest = {
                showAddNoteDialog = false
                selectedVerseForAction = null
            },
            title = { Text(translations.addVerseNote) },
            text = {
                Column {
                    OutlinedTextField(
                        value = noteTitleText,
                        onValueChange = { noteTitleText = it },
                        label = { Text(translations.noteTitle) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteInputText,
                        onValueChange = { noteInputText = it },
                        label = { Text(translations.noteContent) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (noteTitleText.isNotBlank()) {
                        onAddNote(noteTitleText, noteInputText, verseRefLabel)
                    }
                    showAddNoteDialog = false
                    selectedVerseForAction = null
                }) {
                    Text(translations.saveNote)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showAddNoteDialog = false
                    selectedVerseForAction = null
                }) {
                    Text(translations.cancel)
                }
            }
        )
    }
}
