package com.example.exolog

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Nav() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") {
            Login(navController)
        }
        composable("Facture") {
            Facture(navController)

        }

        composable("Total/{calculTTC1}") { backstackEntry ->
            val calculatedTTC = backstackEntry.arguments?.getString("calculTTC1")
            Total(navController, calculatedTTC)
        }

    }
}