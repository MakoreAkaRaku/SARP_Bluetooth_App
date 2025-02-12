package com.example.sarpapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

//private val DarkColorScheme =
/*darkColorScheme(
primary = Color(0xA5A5A5A5),  // Softer blue for dark mode
onPrimary = Color(0xFFFFFFFF), // Dark blue for contrast
primaryContainer = Color(0xFF003366), // Deep blue
onPrimaryContainer = Color(0xFFD3E3FC), // Light blue for readability

secondary = Color(0xFFFFB74D), // Softer orange
onSecondary = Color(0xFF4A2700),
secondaryContainer = Color(0xFF5D4037), // Muted brown-orange
onSecondaryContainer = Color(0xFFFFE0B2),

tertiary = Color(0xFF80CBC4), // Softer teal
onTertiary = Color(0xFF00332C),
tertiaryContainer = Color(0xFF004D40), // Deep teal
onTertiaryContainer = Color(0xFFA7FFEB),

background = Color(0xFF121212), // Almost black
onBackground = Color(0xFFECECEC), // Light gray for readability

surface = Color(0xFF1E1E1E), // Dark gray
onSurface = Color(0xFFD6D6D6), // Softer white text

error = Color(0xFFCF6679), // Muted red for errors
onError = Color(0xFF370B0E) // Darker red contrast
)
*/
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF005FAF), // Deep but friendly blue
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD0E4FF), // Soft blue-tinted background
    onPrimaryContainer = Color(0xFF001C3A),

    secondary = Color(0xFF00897B), // Muted teal
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB2DFDB),
    onSecondaryContainer = Color(0xFF00201A),

    tertiary = Color(0xFFE57373), // Warm coral accent
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDAD6),
    onTertiaryContainer = Color(0xFF410001),

    background = Color(0xFFF7F9FC), // Very light blue-grey, easy on the eyes
    onBackground = Color(0xFF1A1C1E),

    surface = Color.White,
    onSurface = Color(0xFF1A1C1E),
    surfaceVariant = Color(0xFFE1E2E4),
    onSurfaceVariant = Color(0xFF44474E),

    outline = Color(0xFF74777F)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF89C2FF), // Softer blue for dark mode
    onPrimary = Color(0xFF00325B),
    primaryContainer = Color(0xFF004A7F),
    onPrimaryContainer = Color(0xFFD0E4FF),

    secondary = Color(0xFF80CBC4), // Muted teal for dark mode
    onSecondary = Color(0xFF003730),
    secondaryContainer = Color(0xFF005045),
    onSecondaryContainer = Color(0xFFB2DFDB),

    tertiary = Color(0xFFFFA4A2), // Warm coral accent
    onTertiary = Color(0xFF5F1210),
    tertiaryContainer = Color(0xFF8C3030),
    onTertiaryContainer = Color(0xFFFFDAD6),

    background = Color(0xFF121417), // Deep neutral gray, avoiding pitch black
    onBackground = Color(0xFFE3E3E3),

    surface = Color(0xFF1C1E22),
    onSurface = Color(0xFFE3E3E3),
    surfaceVariant = Color(0xFF44474E),
    onSurfaceVariant = Color(0xFFC4C6CA),

    outline = Color(0xFF8C9199)
)


@Composable
fun SARPTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val textColor = when {
        darkTheme -> Color(0xFFFFFFFF)
        else -> Color.Unspecified
    }

    MaterialTheme(
        colorScheme = colorScheme, typography = Typography(
            bodyLarge = TextStyle(
                color = textColor,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 24.sp,
                letterSpacing = 0.5.sp
            )
        ), content = content
    )
}
