package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FavoriteEntity
import com.example.data.model.PublicationEntity
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.GoldAccent

@Composable
fun PublicationDetailScreen(
    publication: PublicationEntity?,
    isFavorite: Boolean,
    translations: AppTranslations,
    onBack: () -> Unit,
    onToggleFavorite: (PublicationEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    var fontSizeSp by remember { mutableIntStateOf(16) }
    var selectedLanguageVersion by remember { mutableStateOf("ORIGINAL") }

    if (publication == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Tsy hita ny lahatsoratra.")
        }
        return
    }

    val displayTitle = when (selectedLanguageVersion) {
        "FR" -> publication.frenchTranslationTitle ?: publication.title
        "EN" -> publication.englishTranslationTitle ?: publication.title
        else -> publication.title
    }

    val displayText = when (selectedLanguageVersion) {
        "FR" -> publication.frenchTranslationText ?: publication.fullText
        "EN" -> publication.englishTranslationText ?: publication.fullText
        else -> publication.fullText
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("publication_detail_screen")
    ) {
        // Detail Header Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
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

                Text(
                    text = publication.type,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )

                // Font size toggle
                IconButton(onClick = {
                    fontSizeSp = if (fontSizeSp >= 22) 14 else fontSizeSp + 2
                }) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = translations.textSize,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Copy entire publication
                IconButton(onClick = {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Publication Text", "$displayTitle\n\n$displayText\n\n— ${publication.author}")
                    clipboard.setPrimaryClip(clip)
                    android.widget.Toast.makeText(context, translations.textCopied, android.widget.Toast.LENGTH_SHORT).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = translations.copyDirect,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                // Favorite
                IconButton(onClick = { onToggleFavorite(publication) }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = translations.addedToFavorites,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Share
                IconButton(onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "${publication.title}\n\n${publication.fullText.take(500)}...\n\n— Solohery RAKOTOARISOA (Mihainoa ny Teny)"
                        )
                        type = "text/plain"
                    }
                    context.startActivity(Intent.createChooser(sendIntent, translations.shareContent))
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = translations.shareContent,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Body Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Official Badge & Author
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "Official",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = translations.officialBadge,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = "${translations.publicationDate} : ${publication.date}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = displayTitle,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    lineHeight = 32.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "${translations.authorTitle} : ${publication.author}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            if (publication.bibleRefs.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    Text(
                        text = "${translations.bibleReferences} : ${publication.bibleRefs}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            // Language versions switcher (if available)
            val hasFr = !publication.frenchTranslationText.isNullOrBlank()
            val hasEn = !publication.englishTranslationText.isNullOrBlank()

            if (hasFr || hasEn) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Fiteny / Langue :",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    OutlinedButton(
                        onClick = { selectedLanguageVersion = "ORIGINAL" },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Malagasy (Jiolahy)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (selectedLanguageVersion == "ORIGINAL") FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }

                    if (hasFr) {
                        OutlinedButton(
                            onClick = { selectedLanguageVersion = "FR" },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Français",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (selectedLanguageVersion == "FR") FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }

                    if (hasEn) {
                        OutlinedButton(
                            onClick = { selectedLanguageVersion = "EN" },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "English",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = if (selectedLanguageVersion == "EN") FontWeight.Bold else FontWeight.Normal
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Text Content (Selectable & Copiable)
            androidx.compose.foundation.text.selection.SelectionContainer {
                Text(
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = fontSizeSp.sp,
                        lineHeight = (fontSizeSp * 1.55).sp
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Practical Applications Box
            if (publication.practicalApplications.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = translations.practicalApplications,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = publication.practicalApplications,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Audio Reference
            if (!publication.audioFileName.isNullOrBlank()) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Audiotrack,
                                contentDescription = "Audio",
                                tint = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = translations.audioOfflineAvailable,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = publication.audioFileName,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Bibliography
            if (publication.bibliography.isNotBlank()) {
                Text(
                    text = "${translations.bibliography} :",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = publication.bibliography,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
