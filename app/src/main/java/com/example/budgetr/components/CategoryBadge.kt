package com.example.budgetr.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetr.models.Category
import com.example.budgetr.ui.theme.Shapes
import com.example.budgetr.ui.theme.Typography

/**
 * A composable function for displaying a category badge.
 *
 * This composable function is responsible for rendering a badge that represents a [Category] with a
 * specified shape, color, and text label. It's typically used to indicate the category of an expense
 * or item.
 *
 * @param category The category to be displayed in the badge.
 * @param modifier Additional [Modifier] to be applied to the badge.
 */
@Composable
fun CategoryBadge(category: Category, modifier: Modifier = Modifier) {
  // Create a Material Design Surface composable, which is used as the background for the badge.
  Surface(
    shape = Shapes.large, // Apply a large rounded shape to the badge.
    color = category.color.copy(alpha = 0.25f), // Set the badge's color with a reduced alpha for transparency.
    modifier = modifier,
  ) {
    // Display the category name as a Text composable within the badge.
    Text(
      category.name,
      color = category.color,
      style = Typography.bodySmall,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp) // Add padding around the text.
    )
  }
}