package plat.lab.applaboratorio.locations.ui.list


import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.ErrorScreen
import plat.lab.applaboratorio.LoadingScreen
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.character.VM.CharacterList.CharacterScreenEvent
import plat.lab.applaboratorio.locations.VM.LocationList.LocationScreenEvent
import plat.lab.applaboratorio.locations.VM.LocationList.LocationScreenState
import plat.lab.applaboratorio.locations.VM.LocationList.LocationVM
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
fun LocationListRoute(onLocationClick: (Int) -> Unit,
                      viewModel: LocationVM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LocationList(onLocationClick = onLocationClick,
        state = state,
        onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationList(modifier: Modifier = Modifier,
                         onLocationClick: (Int) -> Unit,
                         state: LocationScreenState,
                         onEvent: (LocationScreenEvent) -> Unit
) {
    val LocationList: List<Location> = state.data

    if (state.isLoading) {
        LoadingScreen(
            onLoading = { onEvent(LocationScreenEvent.onLoadingScreen) })

    } else if (state.hasError) {
        ErrorScreen(
            onRetry = { onEvent(LocationScreenEvent.onRetry) },
            Error = "Error al obtener listado de localizaciones intenta de nuevo"
        )
    } else {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Locations") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    actions = {
                        IconButton(onClick = { onEvent(LocationScreenEvent.onFavoritesPage) }) {
                            Image(
                                painter = painterResource(id = R.drawable.favorites),
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (state.favorites) {
                    if (LocationList.isEmpty()) {
                        Column(modifier = modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally)
                        {
                            Text("No hay lugares favoritos",
                                style = MaterialTheme.typography.displayLarge)
                        }
                    } else {
                        LazyColumn(modifier = modifier) {
                            items(LocationList.size) {
                                LocationPlate(
                                    name = LocationList[it].name,
                                    tipo = LocationList[it].type,
                                    isFavorite = LocationList[it].isFavorite,
                                    onFavorite = {
                                        onEvent(LocationScreenEvent.onFavorite
                                                (location = LocationList[it])
                                        )},
                                    onLocationClick = {
                                        onLocationClick(LocationList[it].id)
                                    }
                                )
                            }
                        }
                    }
                } else {
                    LazyColumn(modifier = modifier) {
                        items(LocationList.size) { it ->
                            LocationPlate(
                                name = LocationList[it].name,
                                tipo = LocationList[it].type,
                                isFavorite = LocationList[it].isFavorite,
                                onFavorite = {
                                    onEvent(LocationScreenEvent.onFavorite
                                            (location = LocationList[it])
                                    )
                                },
                                onLocationClick = {
                                    onLocationClick(LocationList[it].id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationPlate(modifier: Modifier = Modifier,
                  name: String,
                  tipo: String,
                  isFavorite: Boolean = false,
                  onFavorite: () -> Unit = {},
                  onLocationClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = modifier.padding(start = 5.dp)
            .weight(1f)
            .clickable(onClick = onLocationClick)) {
            Text("$name",
                style = MaterialTheme.typography.titleLarge)
            Text("$tipo",
                style = MaterialTheme.typography.bodyLarge)
        }
        IconButton(onClick = onFavorite) {
            Image(painter = painterResource(id = R.drawable.favorite_star),
                contentDescription = "favorite",
                colorFilter = if (isFavorite) {
                    ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryFixed)
                } else {
                    ColorFilter.tint(MaterialTheme.colorScheme.onSurface)
                },
                modifier = modifier.size(100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    LocationList(onLocationClick = {}, state = LocationScreenState(isLoading = false, hasError = false, data = LocationDb().getAllLocations()), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun LocationLoadingPreview() {
    LocationList(onLocationClick = {}, state = LocationScreenState(isLoading = true, hasError = false), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun LocationErrorPreview() {
    LocationList(onLocationClick = {}, state = LocationScreenState(isLoading = false, hasError = true), onEvent = {})
}
