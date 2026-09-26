package plat.lab.applaboratorio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import plat.lab.applaboratorio.character.characterNestNav
import plat.lab.applaboratorio.character.navigateToCharacterNest
import plat.lab.applaboratorio.character.ui.details.CharacterDetailsDestination
import plat.lab.applaboratorio.locations.locationsNestNav
import plat.lab.applaboratorio.locations.ui.details.LocationDetailsDestination
import plat.lab.applaboratorio.login.ui.LoginDestination
import plat.lab.applaboratorio.login.ui.loginScreen


@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val mostrarBottomBar = currentDestination != null &&
            !currentDestination.hasRoute<LoginDestination>() &&
            !currentDestination.hasRoute<CharacterDetailsDestination>() &&
            !currentDestination.hasRoute<LocationDetailsDestination>()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (mostrarBottomBar) {
                NavigationBar {
                    bottomBarDestinations.forEach { destination ->
                        val seleccionado = currentDestination.hierarchy.any { it.hasRoute(destination.route::class) }
                        NavigationBarItem(
                            selected = seleccionado,
                            onClick = {
                                navController.navigate(destination.route) {
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                androidx.compose.material3.Icon(
                                    painter = painterResource(id = destination.icon),
                                    contentDescription = destination.label,
                                    modifier = modifier.size(30.dp)
                                )
                            },
                            label = {
                                Text(destination.label)
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination =  LoginDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            loginScreen(
                onEmpezar = {
                    navController.navigateToCharacterNest(
                        navOptions { popUpTo(LoginDestination) { inclusive = true } }
                    )
                }
            )
            characterNestNav(navController)
            locationsNestNav(navController)
        }
    }
}