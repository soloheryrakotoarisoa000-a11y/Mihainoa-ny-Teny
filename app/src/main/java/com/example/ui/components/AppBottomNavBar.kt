package com.example.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.i18n.AppTranslations
import com.example.ui.viewmodel.Screen

@Composable
fun AppBottomNavBar(
    currentScreen: Screen,
    translations: AppTranslations,
    onNavigate: (Screen) -> Unit
) {
    val isHome = currentScreen is Screen.Home
    val isLibrary = currentScreen is Screen.PublicationsList || currentScreen is Screen.PublicationDetail
    val isBible = currentScreen is Screen.BibleReader || currentScreen is Screen.BibleCompare
    val isStudy = currentScreen is Screen.Exegesis || currentScreen is Screen.Commentaries
    val isPersonal = currentScreen is Screen.UserStudy

    NavigationBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("app_bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        // Home
        NavigationBarItem(
            selected = isHome,
            onClick = { onNavigate(Screen.Home) },
            icon = {
                Icon(
                    imageVector = if (isHome) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = translations.navHome
                )
            },
            label = {
                Text(
                    text = translations.navHome,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isHome) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_item_home")
        )

        // Library (Publications)
        NavigationBarItem(
            selected = isLibrary,
            onClick = { onNavigate(Screen.PublicationsList()) },
            icon = {
                Icon(
                    imageVector = if (isLibrary) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                    contentDescription = translations.navLibrary
                )
            },
            label = {
                Text(
                    text = translations.navLibrary.split("&").first().trim(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isLibrary) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_item_library")
        )

        // Bible
        NavigationBarItem(
            selected = isBible,
            onClick = { onNavigate(Screen.BibleReader()) },
            icon = {
                Icon(
                    imageVector = if (isBible) Icons.Filled.AutoStories else Icons.Outlined.AutoStories,
                    contentDescription = translations.navBible
                )
            },
            label = {
                Text(
                    text = translations.navBible,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isBible) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_item_bible")
        )

        // Study (Exegesis / Commentaries)
        NavigationBarItem(
            selected = isStudy,
            onClick = { onNavigate(Screen.Exegesis()) },
            icon = {
                Icon(
                    imageVector = if (isStudy) Icons.Filled.Psychology else Icons.Outlined.Psychology,
                    contentDescription = translations.navStudy
                )
            },
            label = {
                Text(
                    text = translations.navStudy.split("&").first().trim(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isStudy) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_item_study")
        )

        // Personal (Notes / Favorites / Bookmarks)
        NavigationBarItem(
            selected = isPersonal,
            onClick = { onNavigate(Screen.UserStudy()) },
            icon = {
                Icon(
                    imageVector = if (isPersonal) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = translations.navPersonal
                )
            },
            label = {
                Text(
                    text = translations.navPersonal.split("&").first().trim(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = if (isPersonal) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 11.sp
                    )
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.testTag("nav_item_personal")
        )
    }
}
