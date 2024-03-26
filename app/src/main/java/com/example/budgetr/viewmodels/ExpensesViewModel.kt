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

/**
 * Data class representing the state of the Expenses screen, including selected recurrence, total sum, and the list of expenses.
 *
 * @param recurrence The selected recurrence for expense filtering.
 * @param sumTotal The total sum of expenses within the selected date range.
 * @param expenses A list of expenses filtered by the selected recurrence and date range.
 */
data class ExpensesState(
  val recurrence: Recurrence = Recurrence.Daily,
  val sumTotal: Double = 1250.98,
  val expenses: List<Expense> = listOf()
)

/**
 * ViewModel for the Expenses screen, responsible for managing expense-related functionality and the screen state.
 */
class ExpensesViewModel: ViewModel() {
  private val _uiState = MutableStateFlow(ExpensesState())
  val uiState: StateFlow<ExpensesState> = _uiState.asStateFlow()

  init {
    _uiState.update { currentState ->
      currentState.copy(
        expenses = db.query<Expense>().find()
      )
    }
    viewModelScope.launch(Dispatchers.IO) {
      setRecurrence(Recurrence.Daily)
    }
  }

  /**
   * Sets the selected recurrence and updates the list of expenses and total sum accordingly.
   *
   * @param recurrence The selected recurrence for expense filtering.
   */
  fun setRecurrence(recurrence: Recurrence) {
    val (start, end) = calculateDateRange(recurrence, 0)

    val filteredExpenses = db.query<Expense>().find().filter { expense ->
      (expense.date.toLocalDate().isAfter(start) && expense.date.toLocalDate()
        .isBefore(end)) || expense.date.toLocalDate()
        .isEqual(start) || expense.date.toLocalDate().isEqual(end)
    }

    val sumTotal = filteredExpenses.sumOf { it.amount }

    _uiState.update { currentState ->
      currentState.copy(
        recurrence = recurrence,
        expenses = filteredExpenses,
        sumTotal = sumTotal
      )
    }
  }
}