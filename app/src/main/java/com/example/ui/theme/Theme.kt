package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = EmeraldPrimaryDark,
    onPrimary = EmeraldOnPrimaryDark,
    primaryContainer = EmeraldContainerDark,
    onPrimaryContainer = EmeraldOnContainerDark,
    secondary = GoldAccentDark,
    onSecondary = Color(0xFF3F2E00),
    secondaryContainer = Color(0xFF5A4400),
    onSecondaryContainer = Color(0xFFFFDF9E),
    tertiary = Color(0xFFA5C8D4),
    onTertiary = Color(0xFF07333F),
    background = DarkBackgroundNight,
    onBackground = DarkTextHighContrast,
    surface = DarkSurfaceNight,
    onSurface = DarkTextHighContrast,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = Color(0xFFBFC9C3),
    outline = OutlineDark
)

private val LightColorScheme = lightColorScheme(
    primary = EmeraldPrimaryLight,
    onPrimary = EmeraldOnPrimaryLight,
    primaryContainer = EmeraldContainerLight,
    onPrimaryContainer = EmeraldOnContainerLight,
    secondary = GoldAccent,
    onSecondary = Color.White,
    secondaryContainer = GoldContainerLight,
    onSecondaryContainer = GoldOnContainerLight,
    tertiary = Color(0xFF396573),
    onTertiary = Color.White,
    background = IvoryBackgroundLight,
    onBackground = Color(0xFF161D1A),
    surface = IvorySurfaceLight,
    onSurface = Color(0xFF161D1A),
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = Color(0xFF404944),
    outline = OutlineLight
)

@Composable
fun MihainoaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
