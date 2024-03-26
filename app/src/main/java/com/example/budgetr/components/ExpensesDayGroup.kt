package com.example.budgetr.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.budgetr.models.DayExpenses
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.ui.theme.Typography
import com.example.budgetr.utils.formatDay
import java.text.DecimalFormat
import java.time.LocalDate

/**
 * A composable function for rendering a group of expenses for a specific day.
 *
 * This composable function displays a group of expenses for a given day, including the date, individual
 * expenses, and the total expenses for that day.
 *
 * @param date The date for which the expenses are displayed.
 * @param dayExpenses The expenses for the specified day.
 * @param modifier Additional [Modifier] to be applied to the group.
 */
@Composable
fun ExpensesDayGroup(
  date: LocalDate,
  dayExpenses: DayExpenses,
  modifier: Modifier = Modifier
) {
  Column(modifier = modifier) {
    // Display the date with a custom format using the formatDay utility function.
    Text(
      date.formatDay(), // Display the formatted date.
      style = Typography.headlineMedium, // Apply a medium-sized headline style to the date.
      color = LabelSecondary
    )
    Divider(modifier = Modifier.padding(top = 10.dp, bottom = 4.dp))

    // Iterate through individual expenses and display each using the ExpenseRow composable.
    dayExpenses.expenses.forEach { expense ->
      ExpenseRow(
        expense = expense,
        modifier = Modifier.padding(top = 12.dp)
      )
    }
    Divider(modifier = Modifier.padding(top = 16.dp, bottom = 4.dp))

    // Display the total expenses for the day with a custom format.
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text("Total:", style = Typography.bodyMedium, color = LabelSecondary)
      Text(
        DecimalFormat("USD 0.#").format(dayExpenses.total), // Format and display the total expenses.
        style = Typography.headlineMedium,
        color = LabelSecondary
      )
    }
  }
}