package com.example.budgetr.components.expensesList

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.budgetr.components.ExpensesDayGroup
import com.example.budgetr.mock.mockExpenses
import com.example.budgetr.models.Expense
import com.example.budgetr.models.groupedByDay
import com.example.budgetr.ui.theme.BudgetrTheme

/**
 * Composable function for rendering a list of expenses grouped by day.
 *
 * @param expenses A list of Expense objects to be displayed in the list.
 * @param modifier Modifier for customizing the layout of the ExpensesList composable.
 */
@Composable
fun ExpensesList(expenses: List<Expense>, modifier: Modifier = Modifier) {
  // Group expenses by day for better organization
  val groupedExpenses = expenses.groupedByDay()

  Column(modifier = modifier) {
    if (groupedExpenses.isEmpty()) {
      // Display a message when there are no expenses for the selected date range
      Text("No data for selected date range.", modifier = Modifier.padding(top = 32.dp))
    } else {
      // Render each day's expenses using ExpensesDayGroup composable
      groupedExpenses.keys.forEach { date ->
        if (groupedExpenses[date] != null) {
          ExpensesDayGroup(
            date = date,
            dayExpenses = groupedExpenses[date]!!,
            modifier = Modifier.padding(top = 24.dp)
          )
        }
      }
    }
  }
}

/**
 * A preview function for ExpensesList with a dark theme (UI_MODE_NIGHT_YES).
 */
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun Preview() {
  BudgetrTheme {
    // Render a preview of ExpensesList with mock expenses
    ExpensesList(mockExpenses)
  }
}