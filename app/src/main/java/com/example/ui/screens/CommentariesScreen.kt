package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TheologicalCommentaryEntity
import com.example.ui.i18n.AppTranslations
import com.example.ui.theme.GoldAccent

@Composable
fun CommentariesScreen(
    commentaries: List<TheologicalCommentaryEntity>,
    translations: AppTranslations,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler { onBack() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("commentaries_screen")
    ) {
        // Top Header
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
                        text = translations.commentariesTitle,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Fanazavana ara-teolojia sy kristôsantrika",
                        style = MaterialTheme.typography.labelSmall.copy(color = GoldAccent)
                    )
                }
            }
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(commentaries) { commentary ->
                CommentaryItemCard(commentary = commentary, translations = translations)
            }
        }
    }
}

@Composable
fun CommentaryItemCard(
    commentary: TheologicalCommentaryEntity,
    translations: AppTranslations
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Passage Title
            Text(
                text = commentary.passageTitle,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "${commentary.author} — ${commentary.workTitle}",
                style = MaterialTheme.typography.labelMedium,
                color = GoldAccent
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Historical Context
            SectionBlock(
                title = translations.historicalContext,
                content = commentary.historicalContext,
                icon = Icons.Default.HistoryEdu,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Literary Structure
            SectionBlock(
                title = translations.passageStructure,
                content = commentary.literaryStructure,
                icon = Icons.Default.Book,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Verse Exposition
            SectionBlock(
                title = translations.verseExposition,
                content = commentary.verseExposition,
                icon = Icons.Default.Lightbulb,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Christocentric Link
            SectionBlock(
                title = translations.christocentricLink,
                content = commentary.christocentricFocus,
                icon = Icons.Default.Stars,
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Homiletic Outlines
            SectionBlock(
                title = translations.homileticOutlines,
                content = commentary.homileticPoints,
                icon = Icons.Default.Lightbulb,
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Small Group Questions
            SectionBlock(
                title = translations.groupStudyQuestions,
                content = commentary.studyQuestions,
                icon = Icons.Default.HelpOutline,
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Credits & License
            Text(
                text = "${translations.commentaryCredits} : ${commentary.license}",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
fun SectionBlock(
    title: String,
    content: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    containerColor: androidx.compose.ui.graphics.Color
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = containerColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(end = 6.dp)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 22.sp),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
