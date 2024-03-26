package com.example.budgetr.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetr.R
import com.example.budgetr.ui.theme.FillTertiary
import com.example.budgetr.ui.theme.BudgetrTheme
import com.example.budgetr.ui.theme.Shapes
import com.example.budgetr.ui.theme.Typography

/**
 * A composable function for rendering a picker trigger.
 *
 * This composable function creates a trigger for a picker, allowing the user to open a picker dialog.
 * It displays a label and an icon that can be clicked to open the picker.
 *
 * @param label The label text for the picker trigger.
 * @param onClick The click action to be performed when the trigger is clicked.
 * @param modifier Additional [Modifier] to be applied to the trigger.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickerTrigger(
  label: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    shape = Shapes.medium, // Set the shape of the trigger.
    color = FillTertiary, // Set the background color of the trigger.
    modifier = modifier,
    onClick = onClick, // Define the action to perform when the trigger is clicked.
  ) {
    Row(
      modifier = Modifier.padding(horizontal = 20.dp, vertical = 3.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(label, style = Typography.titleSmall) // Display the label text with a specific style.
      Icon(
        painterResource(R.drawable.ic_unfold_more), // Set the icon using a drawable resource.
        contentDescription = "Open picker", // Provide content description for accessibility.
        modifier = Modifier.padding(start = 10.dp) // Add padding to the icon.
      )
    }
  }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
  BudgetrTheme {
    PickerTrigger("this week", onClick = {}) // Preview the PickerTrigger composable with a sample label.
  }
}