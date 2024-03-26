package com.example.budgetr.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.budgetr.ui.theme.Destructive
import com.example.budgetr.ui.theme.TextPrimary
import com.example.budgetr.ui.theme.Typography
import com.example.budgetr.R

/**
 * A composable function for rendering a table row.
 *
 * This composable function is responsible for rendering a table row with label, content, and optional
 * detail content. It can also display an arrow icon if specified.
 *
 * @param modifier The modifier for the table row.
 * @param label The label text for the row.
 * @param hasArrow Whether to display a right arrow icon.
 * @param isDestructive Whether the row content is considered destructive.
 * @param detailContent The optional detail content composable.
 * @param content The main content of the row.
 */
@Composable
fun TableRow(
  modifier: Modifier = Modifier,
  label: String? = null,
  hasArrow: Boolean = false,
  isDestructive: Boolean = false,
  detailContent: (@Composable RowScope.() -> Unit)? = null,
  content: (@Composable RowScope.() -> Unit)? = null
) {
  /*
   * Determines the text color based on destructiveness.
   * Destructiveness is used to indicate that the action will result in data loss,
   * e.g if the user were to delete an expense category, their settings, or profile
   */
  val textColor = if (isDestructive) Destructive else TextPrimary

  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (label != null) {
      Text(
        text = label,
        style = Typography.bodyMedium,
        color = textColor,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
      )
    }
    if (content != null) {
      content() // Render the main content of the row if provided.
    }
    if (hasArrow) {
      Icon(
        painterResource(id = R.drawable.chevron_right),
        contentDescription = "Right arrow",
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
      )
    }
    if (detailContent != null) {
      detailContent() // Render optional detail content if provided.
    }
  }
}