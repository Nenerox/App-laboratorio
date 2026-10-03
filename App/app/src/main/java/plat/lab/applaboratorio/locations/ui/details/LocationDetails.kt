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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.ErrorScreen
import plat.lab.applaboratorio.LoadingScreen
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.character.VM.CharacterList.LocationDetailsVM
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailEvent
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailScreenState
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
    composable<LocationDetailsDestination> {
        LocationDetailRoute(
            onBackArrow = onBackArrow
        )
    }
}

@Composable
fun LocationDetailRoute(
    onBackArrow: () -> Unit,
    viewModel: LocationDetailsVM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LocationDetails(
        onBackArrow = onBackArrow,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationDetails(modifier: Modifier = Modifier,
                             onBackArrow: () -> Unit,
                            state: LocationDetailScreenState,
                            onEvent: (LocationDetailEvent) -> Unit
) {
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
            val location = state.data
            if (state.isLoading) {
                LoadingScreen(onLoading = {onEvent(LocationDetailEvent.onLoadingScreen)} )
            } else if (state.hasError || location == null) {
                ErrorScreen(
                    onRetry = {onEvent(LocationDetailEvent.onRetry)},
                    Error = "Error al obtener los detalles de la localización intenta de nuevo" )
            } else {

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
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsPreview(){
    LocationDetails(onBackArrow = {}, state = LocationDetailScreenState(isLoading = false, hasError = false), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsLoadingPreview(){
    LocationDetails(onBackArrow = {}, state = LocationDetailScreenState(isLoading = true, hasError = false), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun LocationDetailsErrorPreview(){
    LocationDetails(onBackArrow = {}, state = LocationDetailScreenState(isLoading = false, hasError = true), onEvent = {})
}