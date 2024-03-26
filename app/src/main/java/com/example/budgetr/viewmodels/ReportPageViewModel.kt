package com.example.budgetr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetr.db
import com.example.budgetr.models.Expense
import com.example.budgetr.models.Recurrence
import com.example.budgetr.utils.calculateDateRange
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Data class representing the state of the ReportPageViewModel.
 *
 * @param expenses List of expenses within the selected date range.
 * @param dateStart The start date of the selected date range.
 * @param dateEnd The end date of the selected date range.
 * @param avgPerDay The average expenses per day within the date range.
 * @param totalInRange The total expenses within the date range.
 */
data class State(
    val expenses: List<Expense> = listOf(),
    val dateStart: LocalDateTime = LocalDateTime.now(),
    val dateEnd: LocalDateTime = LocalDateTime.now(),
    val avgPerDay: Double = 0.0,
    val totalInRange: Double = 0.0
)

/**
 * ViewModel class for a single report page within a specific date range.
 *
 * @param page The page number representing the report's position.
 * @param recurrence The selected recurrence type (e.g., daily, weekly, monthly).
 */
class ReportPageViewModel(private val page: Int, val recurrence: Recurrence) :
  ViewModel() {
  private val _uiState = MutableStateFlow(State())
  val uiState: StateFlow<State> = _uiState.asStateFlow()

  init {
    viewModelScope.launch(Dispatchers.IO) {
      // Calculate the date range based on the specified recurrence and page number.
      val (start, end, daysInRange) = calculateDateRange(recurrence, page)

      // Filter expenses falling within the calculated date range.
      val filteredExpenses = db.query<Expense>().find().filter { expense ->
        (expense.date.toLocalDate().isAfter(start) && expense.date.toLocalDate()
          .isBefore(end)) || expense.date.toLocalDate()
          .isEqual(start) || expense.date.toLocalDate().isEqual(end)
      }

      // Calculate the total expenses within the date range and the average per day.
      val totalExpensesAmount = filteredExpenses.sumOf { it.amount }
      val avgPerDay: Double = totalExpensesAmount / daysInRange

      viewModelScope.launch(Dispatchers.Main) {
        _uiState.update { currentState ->
          currentState.copy(
            dateStart = LocalDateTime.of(start, LocalTime.MIN),
            dateEnd = LocalDateTime.of(end, LocalTime.MAX),
            expenses = filteredExpenses,
            avgPerDay = avgPerDay,
            totalInRange = totalExpensesAmount
          )
        }
      }
    }
  }
}