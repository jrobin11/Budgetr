package com.example.budgetr.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetr.db
import com.example.budgetr.models.Category
import com.example.budgetr.models.Expense
import com.example.budgetr.models.Recurrence
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Data class representing the state of the Add screen, including input fields and category information.
 *
 * @param amount The expense amount as a string.
 * @param recurrence The selected recurrence pattern.
 * @param date The selected date for the expense.
 * @param note Additional notes for the expense.
 * @param category The selected category for the expense.
 * @param categories A collection of available categories.
 */
data class AddScreenState(
  val amount: String = "",
  val recurrence: Recurrence = Recurrence.None,
  val date: LocalDate = LocalDate.now(),
  val note: String = "",
  val category: Category? = null,
  val categories: RealmResults<Category>? = null
)

/**
 * ViewModel for the Add screen, responsible for handling user input and managing the state of the screen.
 */
class AddViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(AddScreenState())
  val uiState: StateFlow<AddScreenState> = _uiState.asStateFlow()

  init {
    _uiState.update { currentState ->
      currentState.copy(
        categories = db.query<Category>().find()
      )
    }
  }

  /**
   * Updates the expense amount in the current state.
   *
   * @param amount The new expense amount as a string.
   */
  fun setAmount(amount: String) {
    var parsed = amount.toDoubleOrNull()

    if (amount.isEmpty()) {
      parsed = 0.0
    }

    if (parsed != null) {
      _uiState.update { currentState ->
        currentState.copy(
          amount = amount.trim().ifEmpty { "0" },
        )
      }
    }
  }

  /**
   * Updates the selected recurrence pattern in the current state.
   *
   * @param recurrence The selected recurrence pattern.
   */
  fun setRecurrence(recurrence: Recurrence) {
    _uiState.update { currentState ->
      currentState.copy(
        recurrence = recurrence,
      )
    }
  }

  /**
   * Updates the selected date in the current state.
   *
   * @param date The selected date for the expense.
   */
  fun setDate(date: LocalDate) {
    _uiState.update { currentState ->
      currentState.copy(
        date = date,
      )
    }
  }

  /**
   * Updates the expense note in the current state.
   *
   * @param note Additional notes for the expense.
   */
  fun setNote(note: String) {
    _uiState.update { currentState ->
      currentState.copy(
        note = note,
      )
    }
  }

  /**
   * Updates the selected category in the current state.
   *
   * @param category The selected category for the expense.
   */
  fun setCategory(category: Category) {
    _uiState.update { currentState ->
      currentState.copy(
        category = category,
      )
    }
  }

  /**
   * Submits the expense to the database and clears the current state.
   */
  fun submitExpense() {
    if (_uiState.value.category != null) {
      viewModelScope.launch(Dispatchers.IO) {
        val now = LocalDateTime.now()
        db.write {
          this.copyToRealm(
            Expense(
              _uiState.value.amount.toDouble(),
              _uiState.value.recurrence,
              _uiState.value.date.atTime(now.hour, now.minute, now.second),
              _uiState.value.note,
              this.query<Category>("_id == $0", _uiState.value.category!!._id)
                .find().first(),
            )
          )
        }
        _uiState.update { currentState ->
          currentState.copy(
            amount = "",
            recurrence = Recurrence.None,
            date = LocalDate.now(),
            note = "",
            category = null,
            categories = null
          )
        }
      }
    }
  }
}