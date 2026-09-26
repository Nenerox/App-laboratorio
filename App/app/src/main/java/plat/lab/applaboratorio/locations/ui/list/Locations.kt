package plat.lab.applaboratorio.locations.ui.list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.locations.data.Location
import plat.lab.applaboratorio.locations.data.LocationDb

@Serializable
data object LocationListDestination

fun NavController.navigateToLocations(navOptions: NavOptions? = null) {
    navigate(LocationListDestination, navOptions)
}

fun NavGraphBuilder.locationsScreen(onLocationClick: (Int) -> Unit) {
    composable<LocationListDestination> {
        LocationListRoute(onLocationClick = onLocationClick)
    }
}

@Composable
fun LocationListRoute(onLocationClick: (Int) -> Unit) {
    LocationList(onLocationClick = onLocationClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationList(modifier: Modifier = Modifier,
                         onLocationClick: (Int) -> Unit){
    val LocationList: List<Location>
    LocationList = LocationDb().getAllLocations()

    Scaffold(modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                    )
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)){
            LazyColumn(modifier = modifier) {
                items(LocationList.size) { it ->
                    LocationPlate(name = LocationList[it].name,
                        tipo = LocationList[it].type,
                        modifier = Modifier.clickable(enabled = true,
                            onClick = {onLocationClick(LocationList[it].id)})
                    )
                }
            }
        }
    }
}

@Composable
fun LocationPlate(modifier: Modifier = Modifier,
                   name: String,
                   tipo: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.padding(start = 5.dp)) {
            Text("$name",
                style = MaterialTheme.typography.titleLarge)
            Text("$tipo",
                style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    LocationList(onLocationClick = {})
}