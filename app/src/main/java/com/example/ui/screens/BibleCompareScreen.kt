package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.filled.ViewColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BibleVerseEntity
import com.example.data.repository.AppRepository
import com.example.ui.i18n.AppLanguage
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.GoldAccent

@Composable
fun BibleCompareScreen(
    initialBookNumber: Int,
    initialChapter: Int,
    initialVerse: Int,
    repository: AppRepository,
    translations: AppTranslations,
    currentLanguage: AppLanguage,
    onBack: () -> Unit,
    onToggleFavorite: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current

    var selectedBookNumber by remember { mutableIntStateOf(initialBookNumber) }
    var selectedChapter by remember { mutableIntStateOf(initialChapter) }
    var selectedVerse by remember { mutableIntStateOf(initialVerse) }
    var isStackedLayout by remember { mutableStateOf(true) }

    val selectedVersionCodes = remember { mutableStateListOf("MBP", "LSG1910", "NA28", "BHS") }

    val books by repository.bibleBooks.collectAsState(initial = emptyList())
    val versions by repository.bibleVersions.collectAsState(initial = emptyList())
    val currentBook = books.find { it.bookNumber == selectedBookNumber } ?: books.firstOrNull()

    val currentBookName = when (currentLanguage) {
        AppLanguage.FRENCH -> currentBook?.nameFr ?: "Livre"
        AppLanguage.ENGLISH -> currentBook?.nameEn ?: "Book"
        else -> currentBook?.nameMg ?: "Boky"
    }

    val versesAcrossVersions by repository.getVerseAcrossVersions(selectedBookNumber, selectedChapter, selectedVerse)
        .collectAsState(initial = emptyList())

    val filteredVerses = versesAcrossVersions.filter { selectedVersionCodes.contains(it.versionCode) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("bible_compare_screen")
    ) {
        // Top Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
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

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = translations.compareTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "$currentBookName $selectedChapter:$selectedVerse",
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent)
                    )
                }

                // Toggle View Layout
                IconButton(onClick = { isStackedLayout = !isStackedLayout }) {
                    Icon(
                        imageVector = if (isStackedLayout) Icons.Default.ViewColumn else Icons.Default.ViewAgenda,
                        contentDescription = if (isStackedLayout) translations.viewColumns else translations.viewStacked,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Quick Passage Selectors (John 1:1, Gen 1:1, Ps 23:1, Rom 8:28)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val samplePassages = listOf(
                Triple(43, 1, 1) to "Jaona 1:1",
                Triple(1, 1, 1) to "Gen 1:1",
                Triple(19, 23, 1) to "Sal 23:1",
                Triple(45, 8, 28) to "Rom 8:28"
            )

            samplePassages.forEach { (ref, label) ->
                val isSelected = selectedBookNumber == ref.first && selectedChapter == ref.second && selectedVerse == ref.third
                FilterChip(
                    selected = isSelected,
                    onClick = {
                        selectedBookNumber = ref.first
                        selectedChapter = ref.second
                        selectedVerse = ref.third
                    },
                    label = { Text(label, style = MaterialTheme.typography.labelSmall) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }

        // Version Selector Chips
        Text(
            text = translations.selectVersionsHint,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val availableCodes = listOf("MBP", "LSG1910", "KJV", "BHS", "NA28")
            availableCodes.forEach { code ->
                val isChecked = selectedVersionCodes.contains(code)
                FilterChip(
                    selected = isChecked,
                    onClick = {
                        if (isChecked && selectedVersionCodes.size > 1) {
                            selectedVersionCodes.remove(code)
                        } else if (!isChecked) {
                            selectedVersionCodes.add(code)
                        }
                    },
                    label = { Text(code) },
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Verses Comparison Display
        if (filteredVerses.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tsy misy andininy voatahiry ho an'ity safidy ity.\nKitiho ireo santionany ambony (Jaona 1:1, Gen 1:1, Sal 23:1, Rom 8:28).",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            androidx.compose.foundation.text.selection.SelectionContainer {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                items(filteredVerses) { vItem ->
                    val vObj = versions.find { it.code == vItem.versionCode }
                    val isRtl = vItem.versionCode == "BHS"

                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.elevatedCardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Version Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Text(
                                            text = vItem.versionCode,
                                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = vObj?.name ?: vItem.versionCode,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.outline,
                                        maxLines = 1
                                    )
                                }

                                Row {
                                    // Copy
                                    IconButton(
                                        onClick = {
                                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                            val clip = ClipData.newPlainText("Verse", "${vItem.text} (${vItem.versionCode})")
                                            clipboard.setPrimaryClip(clip)
                                            android.widget.Toast.makeText(context, translations.verseCopiedDirect, android.widget.Toast.LENGTH_SHORT).show()
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = "Copy",
                                            modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.outline
                                        )
                                    }

                                    // Favorite
                                    IconButton(
                                        onClick = {
                                            onToggleFavorite(
                                                "COMPARE_${vItem.bookNumber}_${vItem.chapter}_${vItem.verse}_${vItem.versionCode}",
                                                "VERSE",
                                                "$currentBookName ${vItem.chapter}:${vItem.verse}",
                                                vItem.versionCode,
                                                vItem.text
                                            )
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Favorite",
                                            modifier = Modifier.size(18.dp),
                                            tint = Color.Red
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Verse Text
                            Text(
                                text = vItem.text,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    lineHeight = 26.sp,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Normal
                                ),
                                textAlign = if (isRtl) TextAlign.Right else TextAlign.Left,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Legal / Source tag
                            if (vObj != null) {
                                Text(
                                    text = "Loharano / Source : ${vObj.publisher} (${vObj.license})",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
            }
        }
    }
}
