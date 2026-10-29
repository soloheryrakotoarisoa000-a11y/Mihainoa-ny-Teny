package com.example.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.example.data.model.PublicationEntity
import com.example.ui.i18n.AppTranslations
import com.example.ui.viewmodel.Screen

@Composable
fun PublicationsScreen(
    translations: AppTranslations,
    publications: List<PublicationEntity>,
    initialFilterType: String? = null,
    initialSearchQuery: String? = null,
    onNavigateDetail: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedType by remember { mutableStateOf(initialFilterType ?: "ALL") }
    var searchQuery by remember { mutableStateOf(initialSearchQuery ?: "") }

    val filterOptions = listOf(
        "ALL" to translations.filterAll,
        "SERMON" to translations.filterSermon,
        "CONFERENCE" to translations.filterConference,
        "BIBLE_STUDY" to translations.filterBibleStudy,
        "DOCTRINE" to translations.filterDoctrine,
        "TRAINING" to translations.filterTraining,
        "HOMILETIC_PLAN" to translations.filterHomileticPlan
    )

    val filteredList = publications.filter { pub ->
        val matchesType = if (selectedType == "ALL") true else pub.type.equals(selectedType, ignoreCase = true)
        val matchesQuery = if (searchQuery.isBlank()) true else {
            pub.title.contains(searchQuery, ignoreCase = true) ||
            pub.summary.contains(searchQuery, ignoreCase = true) ||
            pub.fullText.contains(searchQuery, ignoreCase = true) ||
            pub.bibleRefs.contains(searchQuery, ignoreCase = true) ||
            pub.categories.contains(searchQuery, ignoreCase = true)
        }
        matchesType && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("publications_screen")
    ) {
        // Search Input
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .testTag("publications_search_input"),
            placeholder = { Text(translations.searchPlaceholder) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            shape = RoundedCornerShape(14.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary
            )
        )

        // Filter Chips Horizontal Scroll
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            filterOptions.forEach { (typeKey, label) ->
                val isSelected = selectedType == typeKey
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedType = typeKey },
                    label = { Text(label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Results Count
        Text(
            text = "${filteredList.size} ${translations.navLibrary.lowercase()}",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )

        if (filteredList.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Tsy misy lahatsoratra mifanaraka amin'ny sivana.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(filteredList) { pub ->
                    PublicationCardVertical(
                        publication = pub,
                        translations = translations,
                        onClick = { onNavigateDetail(pub.id) }
                    )
                }
            }
        }
    }
}
