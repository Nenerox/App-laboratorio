package plat.lab.applaboratorio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import plat.lab.applaboratorio.ui.theme.AppLaboratorioTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppLaboratorioTheme {
                NavigationHost()
            }
        }
    }
}