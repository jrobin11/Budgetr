package com.example.budgetr.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.budgetr.components.TableRow
import com.example.budgetr.db
import com.example.budgetr.models.Category
import com.example.budgetr.models.Expense
import com.example.budgetr.ui.theme.BackgroundElevated
import com.example.budgetr.ui.theme.DividerColor
import com.example.budgetr.ui.theme.Shapes
import com.example.budgetr.ui.theme.TopAppBarBackground
import io.realm.kotlin.ext.query
import kotlinx.coroutines.launch

/**
 * Composable function for the "Settings" page in the Budgetr app.
 *
 * @param navController The NavController used for navigation within the app.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(navController: NavController) {
  // Create a coroutine scope to handle background tasks
  val coroutineScope = rememberCoroutineScope()
  // State for controlling the delete confirmation dialog
  var deleteConfirmationShowing by remember {
    mutableStateOf(false)
  }

  // Function to erase all data (expenses and categories)
  val eraseAllData: () -> Unit = {
    coroutineScope.launch {
      // Use Firebase database to query and delete all expenses and categories
      db.write {
        val expenses = this.query<Expense>().find()
        val categories = this.query<Category>().find()

        delete(expenses)
        delete(categories)

        // Close the delete confirmation dialog
        deleteConfirmationShowing = false
      }
    }
  }

  // Scaffold for the "Settings" page
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text("Settings") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
          containerColor = TopAppBarBackground
        )
      )
    },
    content = { innerPadding ->
      Column(modifier = Modifier.padding(innerPadding)) {
        Column(
          modifier = Modifier
            .padding(16.dp)
            .clip(Shapes.large)
            .background(BackgroundElevated)
            .fillMaxWidth()
        ) {
          // Settings table row for navigating to Categories settings
          TableRow(
            label = "Categories",
            hasArrow = true,
            modifier = Modifier.clickable {
              navController.navigate("settings/categories")
            })
          Divider(
            modifier = Modifier
              .padding(start = 16.dp), thickness = 1.dp, color = DividerColor
          )
          // Settings table row for erasing all data, with a destructive action
          TableRow(
            label = "Erase all data",
            isDestructive = true,
            modifier = Modifier.clickable {
              deleteConfirmationShowing = true
            })

          // Display a delete confirmation dialog if `deleteConfirmationShowing` is true
          if (deleteConfirmationShowing) {
            AlertDialog(
              onDismissRequest = { deleteConfirmationShowing = false },
              title = { Text("Are you sure?") },
              text = { Text("This action cannot be undone.") },
              confirmButton = {
                // Button to confirm and delete all data
                TextButton(onClick = eraseAllData) {
                  Text("Delete everything")
                }
              },
              dismissButton = {
                // Button to cancel the delete operation
                TextButton(onClick = { deleteConfirmationShowing = false }) {
                  Text("Cancel")
                }
              }
            )
          }
        }
      }
    }
  )
}