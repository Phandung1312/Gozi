package com.app.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimary,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    surface = DarkGray,
    background = Color.Black,
    onSurface = Color.White,
    onBackground = Color.White,
    surfaceTint = DarkGray,
    surfaceVariant = DarkGray,
    surfaceContainerHighest = DarkGray,
    surfaceContainerLow = DarkGray,
    surfaceContainerLowest = DarkGray,
    surfaceContainer = DarkGray,
    surfaceContainerHigh = DarkGray,
    surfaceDim = DarkGray,
    surfaceBright = DarkGray
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    onPrimary = OnPrimary,
    surface = SurfaceColor,
    secondary = SecondaryColor,
    tertiary = TertiaryColor,
    background = Color.White,
    surfaceTint = Color.White,
    surfaceVariant = Color.White,
    surfaceContainerHighest = Color.White,
    surfaceContainerLow = Color.White,
    surfaceContainerLowest = Color.White,
    surfaceContainer = Color.White,
    surfaceContainerHigh = Color.White,
    surfaceDim = Color.White,
    surfaceBright = Color.White
)

@Composable
fun GoziTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    fontFamily: FontFamily = Rubik,
    dynamicColor: Boolean = false, // Tắt dynamic color để sử dụng PrimaryColor tùy chỉnh
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val typography = getTypography(fontFamily)
    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}