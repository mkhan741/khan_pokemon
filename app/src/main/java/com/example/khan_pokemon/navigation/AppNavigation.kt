package com.example.khan_pokemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.khan_pokemon.ui.PokemonDetailScreen
import com.example.khan_pokemon.ui.PokemonListScreen

@Composable
fun AppNavigation() {

    // navController steuert die Navigation zwischen den Screens
    val navController = rememberNavController()

    // NavHost definiert alle Screens der App
    // startDestination ist der erste Screen der angezeigt wird
    NavHost(navController = navController, startDestination = "list") {

        // der Listenscreen ist unter der Route list erreichbar
        composable(route = "list") {
            PokemonListScreen(navController)
        }

        // der Detailscreen bekommt den Namen des Pokemon aus der Route
        composable(route = "detail/{name}") { backStackEntry ->

            // Namen aus der Route auslesen
            val name = backStackEntry.arguments?.getString("name")

            // nur anzeigen wenn der Name vorhanden ist
            if (name != null) {
                PokemonDetailScreen(name = name, navController = navController)
            }
        }
    }
}