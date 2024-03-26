package com.example.budgetr.components.charts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.example.budgetr.models.Expense
import com.example.budgetr.models.Recurrence
import com.example.budgetr.models.groupedByDayOfMonth
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.utils.simplifyNumber
import java.time.LocalDate
import java.time.YearMonth

/**
 * Composable function for rendering a monthly bar chart of expenses.
 *
 * @param expenses The list of expenses to be displayed in the chart.
 * @param month The LocalDate representing the target month.
 */
@Composable
fun MonthlyChart(expenses: List<Expense>, month: LocalDate) {
  // Group expenses by day of the month.
  val groupedExpenses = expenses.groupedByDayOfMonth()

  // Calculate the number of days in the given month.
  val numberOfDays = YearMonth.of(month.year, month.month).lengthOfMonth()

  // Create a BarChart to display the expenses.
  BarChart(
    barChartData = BarChartData(
      bars = buildList() {
        for (i in 1..numberOfDays) {
          add(BarChartData.Bar(
            label = "$i",
            value = groupedExpenses[i]?.total?.toFloat()
              ?: 0f,
            color = Color.White,
          ))
        }
      }
    ),
    labelDrawer = LabelDrawer(recurrence = Recurrence.Monthly, lastDay = numberOfDays),
    yAxisDrawer = SimpleYAxisDrawer(
      labelTextColor = LabelSecondary,
      labelValueFormatter = ::simplifyNumber,
      labelRatio = 7,
      labelTextSize = 14.sp
    ),
    barDrawer = BarDrawer(recurrence = Recurrence.Monthly),
    modifier = Modifier
      .padding(bottom = 20.dp)
      .fillMaxSize()
  )
}