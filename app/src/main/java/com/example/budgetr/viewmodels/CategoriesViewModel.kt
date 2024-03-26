package com.example.budgetr.viewmodels

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.budgetr.db
import com.example.budgetr.models.Category
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Data class representing the state of the Categories screen, including new category information and the list of categories.
 *
 * @param newCategoryColor The selected color for the new category.
 * @param newCategoryName The name of the new category.
 * @param colorPickerShowing A boolean indicating if the color picker dialog is showing.
 * @param categories A list of categories.
 */
data class CategoriesState(
  val newCategoryColor: Color = Color.White,
  val newCategoryName: String = "",
  val colorPickerShowing: Boolean = false,
  val categories: List<Category> = listOf()
)

/**
 * ViewModel for the Categories screen, responsible for managing category-related functionality and the screen state.
 */
class CategoriesViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(CategoriesState())
  val uiState: StateFlow<CategoriesState> = _uiState.asStateFlow()

  init {
    _uiState.update { currentState ->
      currentState.copy(
        categories = db.query<Category>().find()
      )
    }

    viewModelScope.launch(Dispatchers.IO) {
      db.query<Category>().asFlow().collect { changes ->
        _uiState.update { currentState ->
          currentState.copy(
            categories = changes.list
          )
        }
      }
    }
  }

  /**
   * Updates the selected color for the new category in the current state.
   *
   * @param color The selected color for the new category.
   */
  fun setNewCategoryColor(color: Color) {
    _uiState.update { currentState ->
      currentState.copy(
        newCategoryColor = color
      )
    }
  }

  /**
   * Updates the name of the new category in the current state.
   *
   * @param name The name of the new category.
   */
  fun setNewCategoryName(name: String) {
    _uiState.update { currentState ->
      currentState.copy(
        newCategoryName = name
      )
    }
  }

  /**
   * Shows the color picker dialog.
   */
  fun showColorPicker() {
    _uiState.update { currentState ->
      currentState.copy(
        colorPickerShowing = true
      )
    }
  }

  /**
   * Hides the color picker dialog.
   */
  fun hideColorPicker() {
    _uiState.update { currentState ->
      currentState.copy(
        colorPickerShowing = false
      )
    }
  }

  /**
   * Creates a new category using the selected color and name and clears the related state fields.
   */
  fun createNewCategory() {
    viewModelScope.launch(Dispatchers.IO) {
      db.write {
        this.copyToRealm(
          Category(
          _uiState.value.newCategoryName,
          _uiState.value.newCategoryColor
        )
        )
      }
      _uiState.update { currentState ->
        currentState.copy(
          newCategoryColor = Color.White,
          newCategoryName = ""
        )
      }
    }
  }

  /**
   * Deletes a category from the database.
   *
   * @param category The category to be deleted.
   */
  fun deleteCategory(category: Category) {
    viewModelScope.launch(Dispatchers.IO) {
      db.write {
        val deletingCategory = this.query<Category>("_id == $0", category._id).find().first()
        delete(deletingCategory)
      }
    }
  }
}