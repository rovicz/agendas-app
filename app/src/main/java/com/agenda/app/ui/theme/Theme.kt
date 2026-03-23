package com.agenda.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4A6741),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6B8),
    onPrimaryContainer = Color(0xFF0A2004),
    secondary = Color(0xFF54634D),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD7E8CD),
    onSecondaryContainer = Color(0xFF121F0E),
    tertiary = Color(0xFF386568),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFBCEBEE),
    onTertiaryContainer = Color(0xFF002022),
    background = Color(0xFFFCFDF6),
    onBackground = Color(0xFF1A1C18),
    surface = Color(0xFFFCFDF6),
    onSurface = Color(0xFF1A1C18),
    surfaceVariant = Color(0xFFDFE4D7),
    onSurfaceVariant = Color(0xFF43483E),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    outline = Color(0xFF73796D)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFACCA9E),
    onPrimary = Color(0xFF1C3714),
    primaryContainer = Color(0xFF324F2B),
    onPrimaryContainer = Color(0xFFC8E6B8),
    secondary = Color(0xFFBBCBB2),
    onSecondary = Color(0xFF273422),
    secondaryContainer = Color(0xFF3D4B37),
    onSecondaryContainer = Color(0xFFD7E8CD),
    tertiary = Color(0xFFA0CFD2),
    onTertiary = Color(0xFF003739),
    tertiaryContainer = Color(0xFF1E4D50),
    onTertiaryContainer = Color(0xFFBCEBEE),
    background = Color(0xFF1A1C18),
    onBackground = Color(0xFFE2E3DC),
    surface = Color(0xFF1A1C18),
    onSurface = Color(0xFFE2E3DC),
    surfaceVariant = Color(0xFF43483E),
    onSurfaceVariant = Color(0xFFC3C8BB),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    outline = Color(0xFF8D9286)
)

@Composable
fun AgendaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
