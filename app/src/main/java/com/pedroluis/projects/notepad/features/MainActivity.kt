package com.pedroluis.projects.notepad.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pedroluis.projects.notepad.features.detail.view.DetailScreen
import com.pedroluis.projects.notepad.features.list.view.ListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            ListScreen(
                                onAddNote = {
                                    navController.navigate("detail")
                                },
                                onEditNote = { id, title, description ->
                                    navController.navigate("detail?id=$id&title=$title&description=$description")
                                }
                            )
                        }
                        composable(
                            route = "detail?id={id}&title={title}&description={description}",
                            arguments = listOf(
                                navArgument("id") { nullable = true },
                                navArgument("title") { nullable = true },
                                navArgument("description") { nullable = true }
                            )
                        ) { backStackEntry ->
                            DetailScreen(
                                id = backStackEntry.arguments?.getString("id"),
                                title = backStackEntry.arguments?.getString("title"),
                                description = backStackEntry.arguments?.getString("description"),
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
