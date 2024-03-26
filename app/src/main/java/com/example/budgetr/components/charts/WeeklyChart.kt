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
import com.github.tehras.charts.bar.BarChartData.Bar
import com.github.tehras.charts.bar.renderer.yaxis.SimpleYAxisDrawer
import com.example.budgetr.models.Expense
import com.example.budgetr.models.Recurrence
import com.example.budgetr.models.groupedByDayOfWeek
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.utils.simplifyNumber
import java.time.DayOfWeek

/**
 * A composable function to display a weekly bar chart of expenses.
 *
 * @param expenses List of expenses to be displayed in the chart.
 */
@Composable
fun WeeklyChart(expenses: List<Expense>) {
  // Group expenses by day of the week.
  val groupedExpenses = expenses.groupedByDayOfWeek()

  // Create and display a bar chart with the specified data and settings.
  BarChart(
    barChartData = BarChartData(
      bars = listOf(
        Bar(
          label = DayOfWeek.MONDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat()
            ?: 0f,
          color = Color.White,
        ),
        Bar(
          label = DayOfWeek.TUESDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
        Bar(
          label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
        Bar(
          label = DayOfWeek.THURSDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
        Bar(
          label = DayOfWeek.FRIDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
        Bar(
          label = DayOfWeek.SATURDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
        Bar(
          label = DayOfWeek.SUNDAY.name.substring(0, 1),
          value = groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat() ?: 0f,
          color = Color.White
        ),
      )
    ),
    labelDrawer = LabelDrawer(recurrence = Recurrence.Weekly),
    yAxisDrawer = SimpleYAxisDrawer(
      labelTextColor = LabelSecondary,
      labelValueFormatter = ::simplifyNumber,
      labelRatio = 7,
      labelTextSize = 14.sp
    ),
    barDrawer = BarDrawer(recurrence = Recurrence.Weekly),
    modifier = Modifier
      .padding(bottom = 20.dp)
      .fillMaxSize()
  )
}