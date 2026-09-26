package plat.lab.applaboratorio.profile.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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
data object ProfileDestination

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(ProfileDestination, navOptions)
}

fun NavGraphBuilder.profileScreen(onCerrarSesion: () -> Unit) {
    composable<ProfileDestination> {
        ProfileRoute(onCerrarSesion = onCerrarSesion)
    }
}

@Composable
fun ProfileRoute(onCerrarSesion: () -> Unit) {
    Profile(onCerrarSesion = onCerrarSesion)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Profile(modifier: Modifier = Modifier,
    onCerrarSesion: () -> Unit){
    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .padding(top = 25.dp)
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 20.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Nombre: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "Andres Pineda",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Carne: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "25212",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            FilledTonalButton(onClick = onCerrarSesion
            ) {
                Text(text = "Cerrar Sesión")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
    Profile(onCerrarSesion = {})
}