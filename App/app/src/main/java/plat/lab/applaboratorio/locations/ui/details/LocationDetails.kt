package plat.lab.applaboratorio.locations.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.locations.data.Location
import plat.lab.applaboratorio.locations.data.LocationDb

@Serializable
data class LocationDetailsDestination(val Id: Int)

fun NavController.navigateToLocationDetail(
    Id: Int,
    navOptions: NavOptions? = null
) {
    navigate(LocationDetailsDestination(Id), navOptions)
}

fun NavGraphBuilder.locationDetailScreen(onBackArrow: () -> Unit) {
    composable<LocationDetailsDestination> { backStackEntry ->
        val destination = backStackEntry.toRoute<LocationDetailsDestination>()
        LocationDetailRoute(
            Id = destination.Id,
            onBackArrow = onBackArrow
        )
    }
}

@Composable
fun LocationDetailRoute(
    Id: Int,
    onBackArrow: () -> Unit
) {
    LocationDetails(
        id = Id,
        onBackArrow = onBackArrow
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetails(modifier: Modifier = Modifier,
                             id: Int,
                             onBackArrow: () -> Unit) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    IconButton(onClick = onBackArrow) {
                        Icon(
                            painter = painterResource(id = R.drawable.backarrow),
                            contentDescription = null
                        )
                    }
                },
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
            val location: Location
            location = LocationDb().getLocationById(id)

            Text(
                location.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier.padding(top = 20.dp)
            )

            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 20.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "ID: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "${location.id}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Type: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    location.type,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Dimension: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    location.dimension,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsPreview(){
    LocationDetails(id = 2, onBackArrow = {})
}