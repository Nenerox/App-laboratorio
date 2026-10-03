package plat.lab.applaboratorio.character.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.ErrorScreen
import plat.lab.applaboratorio.LoadingScreen
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailEvent
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import plat.lab.applaboratorio.character.VM.CharacterList.CharacterDetailsVM

@Serializable
data class CharacterDetailsDestination(val Id: Int)

fun NavController.navigateToCharacterDetail(
    Id: Int,
    navOptions: NavOptions? = null
) {
    navigate(CharacterDetailsDestination(Id), navOptions)
}

fun NavGraphBuilder.characterDetailScreen(onBackArrow: () -> Unit) {
    composable<CharacterDetailsDestination> {
        CharacterDetailRoute(
            onBackArrow = onBackArrow
        )
    }
}

@Composable
fun CharacterDetailRoute(
    onBackArrow: () -> Unit,
    viewModel: CharacterDetailsVM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterDetails(
        onBackArrow = onBackArrow,
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterDetails(
    modifier: Modifier = Modifier,
    onBackArrow: () -> Unit,
    state: CharacterDetailScreenState,
    onEvent: (CharacterDetailEvent) -> Unit
){
    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        val character = state.data

        TopAppBar(
            title = { Text("Character Detail") },
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
        if (state.isLoading) {
            LoadingScreen(
                onLoading = {
                    onEvent(CharacterDetailEvent.onLoadingScreen)
                }
            )
        } else if(state.hasError || character == null){
            ErrorScreen(
                Error = "Error al obtener los detalles del personaje intenta de nuevo",
                onRetry = {
                    onEvent(CharacterDetailEvent.onRetry)
                }
            )
        } else {

            AsyncImage(
                model = character.image,
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .size(220.dp)
                    .clip(CircleShape)
            )

            Text(
                character.name,
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 20.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Species: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "${character.species}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Status: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "${character.status}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp, start = 50.dp, end = 50.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Gender: ",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    "${character.gender}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview(){
    CharacterDetails(onBackArrow = {}, state = CharacterDetailScreenState(isLoading = false, hasError = false), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun CharacterLoadingDetailsPreview(){
    CharacterDetails(onBackArrow = {}, state = CharacterDetailScreenState(isLoading = true, hasError = false), onEvent = {})
}

@Preview(showBackground = true)
@Composable
fun CharacterErrorDetailsPreview(){
    CharacterDetails(onBackArrow = {}, state = CharacterDetailScreenState(isLoading = false, hasError = true), onEvent = {})
}