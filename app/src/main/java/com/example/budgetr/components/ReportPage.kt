package com.example.budgetr.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.budgetr.components.charts.MonthlyChart
import com.example.budgetr.components.charts.WeeklyChart
import com.example.budgetr.components.charts.YearlyChart
import com.example.budgetr.components.expensesList.ExpensesList
import com.example.budgetr.models.Recurrence
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.ui.theme.Typography
import com.example.budgetr.utils.formatDayForRange
import com.example.budgetr.viewmodels.ReportPageViewModel
import com.example.budgetr.viewmodels.viewModelFactory
import java.text.DecimalFormat
import java.time.LocalDate

/**
 * A composable function for rendering a report page.
 *
 * This composable function is responsible for displaying a report page that includes charts, expenses,
 * and other information for a specific time range and recurrence.
 *
 * @param innerPadding The padding values for the inner content of the page.
 * @param page The page number for the report.
 * @param recurrence The recurrence (e.g., Weekly, Monthly, Yearly) for the report.
 * @param vm The view model for the report page.
 */
@Composable
fun ReportPage(
  innerPadding: PaddingValues,
  page: Int,
  recurrence: Recurrence,
  vm: ReportPageViewModel = viewModel(
    key = "$page-${recurrence.name}",
    factory = viewModelFactory {
      ReportPageViewModel(page, recurrence)
    })
) {
  // Collect and store the UI state from the view model.
  val uiState = vm.uiState.collectAsState().value

  Column(
    modifier = Modifier
      .padding(innerPadding)
      .padding(horizontal = 16.dp)
      .padding(top = 16.dp)
      .fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    // Display date range and total expenses.
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Column {
        Text(
          "${
            uiState.dateStart.formatDayForRange()
          } - ${uiState.dateEnd.formatDayForRange()}",
          style = Typography.titleSmall
        )
        Row(modifier = Modifier.padding(top = 4.dp)) {
          Text(
            "USD",
            style = Typography.bodyMedium,
            color = LabelSecondary,
            modifier = Modifier.padding(end = 4.dp)
          )
          Text(DecimalFormat("0.#").format(uiState.totalInRange), style = Typography.headlineMedium)
        }
      }
      Column(horizontalAlignment = Alignment.End) {
        Text("Avg/day", style = Typography.titleSmall)
        Row(modifier = Modifier.padding(top = 4.dp)) {
          Text(
            "USD",
            style = Typography.bodyMedium,
            color = LabelSecondary,
            modifier = Modifier.padding(end = 4.dp)
          )
          Text(DecimalFormat("0.#").format(uiState.avgPerDay), style = Typography.headlineMedium)
        }
      }
    }

    // Display charts based on the recurrence.
    Box(
      modifier = Modifier
        .height(180.dp)
        .padding(vertical = 16.dp)
    ) {
      // Display the appropriate chart based on the recurrence.
      when (recurrence) {
        Recurrence.Weekly -> WeeklyChart(expenses = uiState.expenses)
        Recurrence.Monthly -> MonthlyChart(
          expenses = uiState.expenses,
          LocalDate.now()
        )
        Recurrence.Yearly -> YearlyChart(expenses = uiState.expenses)
        else -> Unit
      }
    }

    // Display the list of expenses.
    ExpensesList(
      expenses = uiState.expenses, modifier = Modifier
        .weight(1f)
        .verticalScroll(
          rememberScrollState() // Remember scroll state to scroll to top when the page changes.
        )
    )
  }
}