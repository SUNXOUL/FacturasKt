package com.sagrd.facturasapp.Nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import com.sagrd.facturasapp.FacturasScreen
import androidx.navigation.compose.rememberNavController
import com.sagrd.facturasapp.FormFacturaScreen
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.sagrd.facturasapp.FacturaViewModel

@Composable
fun AppNavigation(
    viewModel: FacturaViewModel = hiltViewModel(),
)
{
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.FirstScreen.route
    ) {
        //Home Screen
        composable(AppScreens.FirstScreen.route) {
            FacturasScreen(navController = navController)
        }
        composable(AppScreens.SecondScreen.route) {
            FormFacturaScreen(navController = navController)
        }
    }
}


