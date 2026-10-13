package plat.lab.applaboratorio.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.core.VM.SessionVM
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Serializable
data object LoginDestination

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginDestination, navOptions)
}

fun NavGraphBuilder.loginScreen(onEmpezar: () -> Unit) {
    composable<LoginDestination> {
        LoginRoute(onEmpezar = onEmpezar)
    }
}

@Composable
fun LoginRoute(onEmpezar: () -> Unit,
               viewModel: SessionVM = viewModel()
) {
    Login(onEmpezar = { nombre ->
        viewModel.saveNombre(nombre)
        onEmpezar() }
    )
}


@Composable
private fun Login(modifier: Modifier = Modifier,
          onEmpezar: (String) -> Unit = {}){
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(start =50.dp, end = 50.dp, top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var nombre by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            placeholder = { Text("Nombre") },
            singleLine = true
        )

        FilledTonalButton(onClick = {
            if (nombre.isNotBlank()) {
                onEmpezar(nombre.trim()) }
                                    },
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) { Text(text = "Iniciar Sesion") }

        Spacer(modifier = Modifier.weight(1f))

        Text("Andres Pineda - #25212",
            style = MaterialTheme.typography.bodyLarge)

    }
}

@Preview
@Composable
fun LoginPreview(){
    Login()
}