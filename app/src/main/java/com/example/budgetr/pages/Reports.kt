package com.example.budgetr.pages

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.example.budgetr.components.ReportPage
import com.example.budgetr.models.Recurrence
import com.example.budgetr.ui.theme.TopAppBarBackground
import com.example.budgetr.viewmodels.ReportsViewModel
import com.example.budgetr.R

/**
 * Composable function for the "Reports" page in the Budgetr app.
 *
 * @param vm The ReportsViewModel used for managing the state of the "Reports" page.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Reports(vm: ReportsViewModel = viewModel()) {
  // Collect and observe the UI state
  val uiState = vm.uiState.collectAsState().value

  // List of available recurrence options
  val recurrences = listOf(
    Recurrence.Weekly,
    Recurrence.Monthly,
    Recurrence.Yearly
  )

  // Scaffold for the "Reports" page
  Scaffold(
    topBar = {
      MediumTopAppBar(
        title = { Text("Reports") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
          containerColor = TopAppBarBackground
        ),
        actions = {
          // Button to open the recurrence menu
          IconButton(onClick = vm::openRecurrenceMenu) {
            Icon(
              painterResource(id = R.drawable.ic_today),
              contentDescription = "Change recurrence"
            )
          }
          // Dropdown menu for selecting recurrence
          DropdownMenu(
            expanded = uiState.recurrenceMenuOpened,
            onDismissRequest = vm::closeRecurrenceMenu
          ) {
            recurrences.forEach { recurrence ->
              DropdownMenuItem(text = { Text(recurrence.name) }, onClick = {
                vm.setRecurrence(recurrence)
                vm.closeRecurrenceMenu()
              })
            }
          }
        }
      )
    },
    content = { innerPadding ->
      // Determine the number of pages based on the selected recurrence
      val numOfPages = when (uiState.recurrence) {
        Recurrence.Weekly -> 53
        Recurrence.Monthly -> 12
        Recurrence.Yearly -> 1
        else -> 53
      }
      // HorizontalPager to navigate between report pages
      HorizontalPager(count = numOfPages, reverseLayout = true) { page ->
        ReportPage(innerPadding, page, uiState.recurrence)
      }
    }
  )
}