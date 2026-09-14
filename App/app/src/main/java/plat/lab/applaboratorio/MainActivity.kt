package plat.lab.applaboratorio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.ui.theme.AppLaboratorioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLaboratorioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable<LoginDestination> {
                            //importar el composable de la pantalla de Login
                        }
                        composable<CharacterListDestination> {
                            //importar el composable de la pantalla de Characters
                        }
                        composable<CharacterDetailsDestination> {
                            //importar el composable de la pantalla de CharacterDetails
                        }
                    }
                }
            }
        }
    }
}
@Serializable
data object LoginDestination

@Serializable
data object CharacterListDestination

@Serializable
data class CharacterDetailsDestination(
    val id: Int
)