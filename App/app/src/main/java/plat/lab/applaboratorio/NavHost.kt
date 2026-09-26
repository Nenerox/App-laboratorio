package plat.lab.applaboratorio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import plat.lab.applaboratorio.character.ui.details.characterDetailScreen
import plat.lab.applaboratorio.character.ui.details.navigateToCharacterDetail
import plat.lab.applaboratorio.character.ui.list.charactersScreen
import plat.lab.applaboratorio.character.ui.list.navigateToCharacters
import plat.lab.applaboratorio.login.ui.LoginDestination
import plat.lab.applaboratorio.login.ui.loginScreen


@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination =  LoginDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            loginScreen(
                onEmpezar = {
                    navController.navigateToCharacters(
                        navOptions { popUpTo(LoginDestination) { inclusive = true } }
                    )
                }
            )

            charactersScreen(onCharacterClick = { id -> navController.navigateToCharacterDetail(id) })
            characterDetailScreen(onBackArrow = { navController.navigateToCharacters() })

        }
    }
}