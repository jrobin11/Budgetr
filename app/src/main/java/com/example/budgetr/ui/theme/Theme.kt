package com.example.budgetr.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

// Define the DarkColorPalette with custom color scheme
private val DarkColorPalette = darkColorScheme(
  primary = Primary,
  background = Surface,
  surface = Surface,
  error = Destructive,
  onPrimary = TextPrimary,
  onSecondary = TextPrimary,
  onBackground = TextPrimary,
  onSurface = TextPrimary,
  onError = TextPrimary,

  /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun BudgetrTheme(
  content: @Composable () -> Unit
) {
  val colors = DarkColorPalette

  // Create and apply the MaterialTheme with custom color scheme, typography, and shapes
  MaterialTheme(
    colorScheme = colors,
    typography = Typography,
    shapes = Shapes,
    content = content
  )
}