package com.example.budgetr.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Custom shape definitions for the app's theme
val Shapes = Shapes(
  small = RoundedCornerShape(2.dp), // Small rounded corners for certain UI elements
  medium = RoundedCornerShape(6.dp), // Medium rounded corners for general elements
  large = RoundedCornerShape(8.dp) // Large rounded corners for specific UI components
)