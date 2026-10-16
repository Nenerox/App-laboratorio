package plat.lab.applaboratorio.character.ui.list

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
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
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.ErrorScreen
import plat.lab.applaboratorio.LoadingScreen
import plat.lab.applaboratorio.R
import plat.lab.applaboratorio.character.VM.CharacterList.CharacterScreenEvent
import plat.lab.applaboratorio.character.VM.CharacterList.CharacterScreenState
import plat.lab.applaboratorio.character.VM.CharacterList.CharacterVM
import plat.lab.applaboratorio.ui.theme.AppLaboratorioTheme


@Serializable
data object CharacterListDestination

fun NavController.navigateToCharacters(navOptions: NavOptions? = null) {
    navigate(CharacterListDestination, navOptions)
}

fun NavGraphBuilder.charactersScreen(onCharacterClick: (Int) -> Unit) {
    composable<CharacterListDestination> {
        CharacterListRoute(onCharacterClick = onCharacterClick)
    }
}

@Composable
fun CharacterListRoute(onCharacterClick: (Int) -> Unit,
                       viewModel: CharacterVM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CharacterList(onCharacterClick = onCharacterClick,
        state = state,
        onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterList(
    modifier: Modifier = Modifier,
    onCharacterClick: (Int) -> Unit,
    state: CharacterScreenState,
    onEvent: (CharacterScreenEvent) -> Unit
){
    val CharacterList: List<Character>
    CharacterList = state.data

    if(state.isLoading) {
        LoadingScreen(
            onLoading = { onEvent(CharacterScreenEvent.onLoadingScreen) })
    } else if(state.hasError) {
        ErrorScreen(
            Error = "Error al obtener listado de personajes intenta de nuevo",
            onRetry = { onEvent(CharacterScreenEvent.onRetry) })
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            TopAppBar(
                title = { Text("Characters") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )

            LazyColumn(modifier = modifier) {
                items(CharacterList.size) { it ->
                    CharacterPlate(
                        name = CharacterList[it].name,
                        status = CharacterList[it].status,
                        species = CharacterList[it].species,
                        link = CharacterList[it].image,
                        isFavorite = CharacterList[it].isFavorite,
                        onFavorite = {},
                        modifier = Modifier.clickable(
                            enabled = true,
                            onClick = { onCharacterClick(CharacterList[it].id) })
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterPlate(modifier: Modifier = Modifier,
                   name: String,
                   status: String,
                   species: String,
                   link: String,
                   isFavorite: Boolean = false,
                   onFavorite: () -> Unit = {})
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = link,
            contentDescription = "Character Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Column(modifier = modifier.padding(start = 5.dp).weight(1f)) {
            Text("$name",
                style = MaterialTheme.typography.titleLarge)
            Text("$species - $status",
                style = MaterialTheme.typography.bodyLarge)
        }
        IconButton(onClick = onFavorite) {
            Image(painter = painterResource(id = R.drawable.favorite_star),
                contentDescription = "Error",
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
fun CharacterLoadingPreview() {
    AppLaboratorioTheme {
        CharacterList(onCharacterClick = {}, state = CharacterScreenState(isLoading = true, hasError = false), onEvent = {})
    }
}
@Preview(showBackground = true)
@Composable
fun CharacterPreview() {
    AppLaboratorioTheme {
    CharacterList(onCharacterClick = {}, state = CharacterScreenState(isLoading = false, hasError = false), onEvent = {})
    }
}
@Preview(showBackground = true)
@Composable
fun CharacterErrorPreview() {
    AppLaboratorioTheme {
    CharacterList(onCharacterClick = {}, state = CharacterScreenState(isLoading = false, hasError = true), onEvent = {})
    }
}
