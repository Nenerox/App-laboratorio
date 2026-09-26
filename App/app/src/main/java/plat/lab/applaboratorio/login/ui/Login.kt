package plat.lab.applaboratorio.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.R

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
fun LoginRoute(onEmpezar: () -> Unit) {
    Login(onEmpezar = onEmpezar)
}


@Composable
private fun Login(modifier: Modifier = Modifier,
          onEmpezar: () -> Unit = {}){
    Column(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background)
        .padding(start =50.dp, end = 50.dp, top = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp))

        FilledTonalButton(onClick = onEmpezar,
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        ) { Text(text = "Empezar") }

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