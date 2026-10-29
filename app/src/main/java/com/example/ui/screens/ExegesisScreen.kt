package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OriginalWordEntity
import com.example.data.repository.AppRepository
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.GoldAccent

@Composable
fun ExegesisScreen(
    initialBookNumber: Int,
    initialChapter: Int,
    initialVerse: Int,
    repository: AppRepository,
    translations: AppTranslations,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    var selectedLanguageTab by remember { mutableIntStateOf(if (initialBookNumber >= 40) 1 else 0) } // 0: Hebrew, 1: Greek
    var selectedPassageIndex by remember { mutableIntStateOf(0) }

    // Pre-configured deep exegesis passages
    val hebrewPassages = listOf(
        Triple(1, 1, 1) to "Genesisy 1:1 (Bereshit / Creatio ex nihilo)",
        Triple(19, 23, 1) to "Salamo 23:1 (Mizmor le-David / Ilay Mpiandry)"
    )

    val greekPassages = listOf(
        Triple(43, 1, 1) to "Jaona 1:1 (En archē ēn ho Logos)",
        Triple(45, 8, 28) to "Romana 8:28 (Panta synergei eis agathon)"
    )

    val currentPassageList = if (selectedLanguageTab == 0) hebrewPassages else greekPassages
    val currentRef = currentPassageList.getOrNull(selectedPassageIndex)?.first ?: (if (selectedLanguageTab == 0) Triple(1, 1, 1) else Triple(43, 1, 1))

    val words by repository.getOriginalWordsForVerse(currentRef.first, currentRef.second, currentRef.third)
        .collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("exegesis_screen")
    ) {
        // Top Header
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

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = translations.exegesisTitle,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (selectedLanguageTab == 0) translations.hebrewAnalysis else translations.greekAnalysis,
                            style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent)
                        )
                    }
                }

                TabRow(selectedTabIndex = selectedLanguageTab) {
                    Tab(
                        selected = selectedLanguageTab == 0,
                        onClick = {
                            selectedLanguageTab = 0
                            selectedPassageIndex = 0
                        },
                        text = { Text("Hébreu (AT / BHS)") }
                    )
                    Tab(
                        selected = selectedLanguageTab == 1,
                        onClick = {
                            selectedLanguageTab = 1
                            selectedPassageIndex = 0
                        },
                        text = { Text("Grec (NT / NA28)") }
                    )
                }
            }
        }

        // Passage Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            currentPassageList.forEachIndexed { idx, pair ->
                FilterChip(
                    selected = selectedPassageIndex == idx,
                    onClick = { selectedPassageIndex = idx },
                    label = { Text(pair.second.split("(").first().trim()) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
            }
        }

        // RTL or Greek explanation badge
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Row(
                modifier = Modifier.padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (selectedLanguageTab == 0) translations.rtlNotice else "Texte grec polytonique avec accents et analyse morphologique pas-à-pas.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        // Words Breakdown List
        if (words.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Dona-kevitra ara-tsoratra ho an'ity andininy ity dia mbola ampidirina.\nSafidio ireo andininy voalohany voarakitra.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(words) { wordItem ->
                    WordAnalysisCard(wordItem = wordItem, translations = translations)
                }

                item {
                    Text(
                        text = translations.lexicalSourceNotice,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun WordAnalysisCard(
    wordItem: OriginalWordEntity,
    translations: AppTranslations
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Original Word Header (RTL for Hebrew, LTR for Greek)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = wordItem.strongNumber,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                // Original Text large and prominent
                Text(
                    text = wordItem.originalText,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = if (wordItem.isRtl) 1.sp else 0.sp
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = if (wordItem.isRtl) TextAlign.Right else TextAlign.Left
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Transliteration & Lemma
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Translittération : [ ${wordItem.transliteration} ]",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        fontWeight = FontWeight.Medium
                    ),
                    color = GoldAccent
                )

                Text(
                    text = "Lemme : ${wordItem.lemma}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Morphology Box
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "${translations.morphology} : ",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = wordItem.morphologyCode,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = wordItem.morphologyDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Definition & Theological Note
            Text(
                text = "${translations.definitionAndContext} :",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = wordItem.definition,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (wordItem.theologicalNote.isNotBlank()) {
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Portée théologique :",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = wordItem.theologicalNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}
