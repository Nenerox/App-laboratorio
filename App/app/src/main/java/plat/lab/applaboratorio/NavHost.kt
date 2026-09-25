package plat.lab.applaboratorio

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.character.ui.details.CharacterDetails
import plat.lab.applaboratorio.character.ui.list.CharacterList
import plat.lab.applaboratorio.login.ui.Login

@Serializable
data object LoginDestination

@Serializable
data object CharacterListDestination

@Serializable
data class CharacterDetailsDestination(
    val id: Int
)

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = LoginDestination,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable<LoginDestination> {
                Login(
                    onEmpezar = {
                        navController.navigate(
                            route = CharacterListDestination
                        ) {
                            popUpTo(LoginDestination) {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<CharacterListDestination> {
                CharacterList(
                    onCharacterClick = { id ->
                        navController.navigate(
                            route = CharacterDetailsDestination(id)
                        )
                    }
                )
            }

            composable<CharacterDetailsDestination> { backStackEntry ->
                val destination: CharacterDetailsDestination = backStackEntry.toRoute()
                CharacterDetails(
                    id = destination.id,
                    onBackArrow = {
                        navController.navigate(
                            route = CharacterListDestination
                        ) {
                        }
                    }
                )
            }
        }
    }
}