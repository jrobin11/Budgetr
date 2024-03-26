package com.example.budgetr.viewmodels

import androidx.lifecycle.ViewModel
import com.example.budgetr.models.Recurrence
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Data class representing the state of the ReportsViewModel.
 *
 * @param recurrence The selected recurrence type for reports (e.g., weekly, monthly).
 * @param recurrenceMenuOpened A flag indicating whether the recurrence menu is open.
 */
data class ReportsState(
    val recurrence: Recurrence = Recurrence.Weekly,
    val recurrenceMenuOpened: Boolean = false
)

/**
 * ViewModel class for managing reports and their recurrence settings.
 */
class ReportsViewModel: ViewModel() {
  private val _uiState = MutableStateFlow(ReportsState())
  val uiState: StateFlow<ReportsState> = _uiState.asStateFlow()

  /**
   * Sets the selected recurrence type for reports.
   *
   * @param recurrence The chosen recurrence type.
   */
  fun setRecurrence(recurrence: Recurrence) {
    _uiState.update { currentState ->
      currentState.copy(
        recurrence = recurrence
      )
    }
  }

  /**
   * Opens the recurrence menu for selecting recurrence settings.
   */
  fun openRecurrenceMenu() {
    _uiState.update { currentState ->
      currentState.copy(
        recurrenceMenuOpened = true
      )
    }
  }

  /**
   * Closes the recurrence menu without making any selections.
   */
  fun closeRecurrenceMenu() {
    _uiState.update { currentState ->
      currentState.copy(
        recurrenceMenuOpened = false
      )
    }
  }
}