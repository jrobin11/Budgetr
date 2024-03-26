package com.example.budgetr.pages

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.budgetr.components.PickerTrigger
import com.example.budgetr.components.expensesList.ExpensesList
import com.example.budgetr.models.Recurrence
import com.example.budgetr.ui.theme.BudgetrTheme
import com.example.budgetr.ui.theme.LabelSecondary
import com.example.budgetr.ui.theme.TopAppBarBackground
import com.example.budgetr.ui.theme.Typography
import com.example.budgetr.viewmodels.ExpensesViewModel
import java.text.DecimalFormat

/**
 * Composable function for the "Expenses" page in the Budgetr app.
 *
 * @param navController The NavController for navigation.
 * @param vm The ExpensesViewModel used for managing the state of the "Expenses" page.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Expenses(
  navController: NavController,
  vm: ExpensesViewModel = viewModel()
) {
  // List of available recurrence options
  val recurrences = listOf(
    Recurrence.Daily,
    Recurrence.Weekly,
    Recurrence.Monthly,
    Recurrence.Yearly
  )

  // Collect and observe the UI state
  val state by vm.uiState.collectAsState()
  // Flag to manage the visibility of recurrence menu
  var recurrenceMenuOpened by remember {
    mutableStateOf(false)
  }

  // Scaffold for the "Expenses" page
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text("Expenses") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
          containerColor = TopAppBarBackground
        )
      )
    },
    content = { innerPadding ->
      Column(
        modifier = Modifier
          .padding(innerPadding)
          .padding(horizontal = 16.dp)
          .padding(top = 16.dp)
          .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Row for selecting the recurrence option
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            "Total for:",
            style = Typography.bodyMedium,
          )
          PickerTrigger(
            state.recurrence.target ?: Recurrence.None.target,
            onClick = { recurrenceMenuOpened = !recurrenceMenuOpened },
            modifier = Modifier.padding(start = 16.dp)
          )
          DropdownMenu(expanded = recurrenceMenuOpened,
            onDismissRequest = { recurrenceMenuOpened = false }) {
            recurrences.forEach { recurrence ->
              DropdownMenuItem(text = { Text(recurrence.target) }, onClick = {
                vm.setRecurrence(recurrence)
                recurrenceMenuOpened = false
              })
            }
          }
        }
        // Display the total expense amount
        Row(modifier = Modifier.padding(vertical = 32.dp)) {
          Text(
            "$",
            style = Typography.bodyMedium,
            color = LabelSecondary,
            modifier = Modifier.padding(end = 4.dp, top = 4.dp)
          )
          Text(
            DecimalFormat("0.#").format(state.sumTotal),
            style = Typography.titleLarge
          )
        }
        // Display the list of expenses
        ExpensesList(
          expenses = state.expenses,
          modifier = Modifier
            .weight(1f)
            .verticalScroll(
              rememberScrollState()
            )
        )
      }
    }
  )
}

/**
 * Preview function for the "Expenses" page in the Budgetr app.
 */
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ExpensesPreview() {
  BudgetrTheme {
    Expenses(navController = rememberNavController())
  }
}