package com.example.budgetr

import android.content.res.Configuration
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.budgetr.pages.*
import com.example.budgetr.ui.theme.BudgetrTheme
import com.example.budgetr.ui.theme.TopAppBarBackground
import io.sentry.compose.withSentryObservableEffect

/**
 * The main activity of the application.
 */
class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterial3Api::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    // Set the soft input mode to adjust for resize.
    window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    setContent {
      BudgetrTheme {
        // Define a state variable to control the visibility of the bottom bar.
        var showBottomBar by rememberSaveable { mutableStateOf(true) }

        // Create a navigation controller with Sentry observability.
        val navController = rememberNavController().withSentryObservableEffect()
        val backStackEntry by navController.currentBackStackEntryAsState()

        // Determine whether to show the bottom bar based on the current destination.
        showBottomBar = when (backStackEntry?.destination?.route) {
          "settings/categories" -> false
          else -> true
        }

        // Create the main application UI using the Scaffold composable.
        Scaffold(
          bottomBar = {
            if (showBottomBar) {
              NavigationBar(containerColor = TopAppBarBackground) {
                // Define navigation items for different destinations.
                NavigationBarItem(
                  selected = backStackEntry?.destination?.route == "expenses",
                  onClick = { navController.navigate("expenses") },
                  label = {
                    Text("Expenses")
                  },
                  icon = {
                    Icon(
                      painterResource(id = R.drawable.upload),
                      contentDescription = "Upload"
                    )
                  }
                )
                NavigationBarItem(
                  selected = backStackEntry?.destination?.route == "reports",
                  onClick = { navController.navigate("reports") },
                  label = {
                    Text("Reports")
                  },
                  icon = {
                    Icon(
                      painterResource(id = R.drawable.bar_chart),
                      contentDescription = "Reports"
                    )
                  }
                )
                NavigationBarItem(
                  selected = backStackEntry?.destination?.route == "add",
                  onClick = { navController.navigate("add") },
                  label = {
                    Text("Add")
                  },
                  icon = {
                    Icon(
                      painterResource(id = R.drawable.add),
                      contentDescription = "Add"
                    )
                  }
                )
                NavigationBarItem(
                  selected = backStackEntry?.destination?.route?.startsWith("settings")
                    ?: false,
                  onClick = { navController.navigate("settings") },
                  label = {
                    Text("Settings")
                  },
                  icon = {
                    Icon(
                      painterResource(id = R.drawable.settings_outlined),
                      contentDescription = "Settings"
                    )
                  }
                )
              }
            }
          },
          content = { innerPadding ->
            // Set up the navigation host with composable destinations.
            NavHost(
              navController = navController,
              startDestination = "expenses"
            ) {
              composable("expenses") {
                // Composable for the "expenses" destination.
                Surface(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                ) {
                  Expenses(navController)
                }
              }
              composable("reports") {
                // Composable for the "reports" destination.
                Surface(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                ) {
                  Reports()
                }
              }
              composable("add") {
                // Composable for the "add" destination.
                Surface(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                ) {
                  Add(navController)
                }
              }
              composable("settings") {
                // Composable for the "settings" destination.
                Surface(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                ) {
                  Settings(navController)
                }
              }
              composable("settings/categories") {
                // Composable for the "settings/categories" destination.
                Surface(
                  modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                ) {
                  Categories(navController)
                }
              }
            }
          }
        )
      }
    }
  }
}

/**
 * A simple composable function for displaying a greeting message.
 *
 * @param name The name to include in the greeting.
 */
@Composable
fun Greeting(name: String) {
  Text(text = "Hello $name!")
}

/**
 * Preview function for the default composable.
 */
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
  BudgetrTheme {
    Surface {
      Greeting("Android")
    }
  }
}