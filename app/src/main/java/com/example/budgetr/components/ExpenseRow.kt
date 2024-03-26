package com.example.budgetr.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetr.models.Expense
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.ui.theme.Typography
import java.text.DecimalFormat
import java.time.format.DateTimeFormatter

/**
 * A composable function for rendering a row representing an expense.
 *
 * This composable function is responsible for displaying information about an expense, including its
 * note, amount, category, and date, in a visually organized row format.
 *
 * @param expense The expense to be displayed in the row.
 * @param modifier Additional [Modifier] to be applied to the row.
 */
@Composable
fun ExpenseRow(expense: Expense, modifier: Modifier = Modifier) {
  Column(modifier = modifier) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        expense.note ?: expense.category!!.name, // Display the note or category name.
        style = Typography.headlineMedium
      )
      Text(
        "USD ${DecimalFormat("0.#").format(expense.amount)}", // Format and display the expense amount.
        style = Typography.headlineMedium
      )
    }
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 6.dp),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      CategoryBadge(category = expense.category!!) // Display the category badge.
      Text(
        expense.date.format(DateTimeFormatter.ofPattern("HH:mm")),
        style = Typography.bodyMedium,
        color = LabelSecondary
      )
    }
  }
}