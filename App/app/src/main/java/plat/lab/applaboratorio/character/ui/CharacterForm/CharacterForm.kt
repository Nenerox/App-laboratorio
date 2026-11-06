package plat.lab.applaboratorio.character.ui.CharacterForm

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.character.VM.CharacterForm.CharacterFormState
import plat.lab.applaboratorio.character.VM.CharacterForm.CharacterFormVM

@Serializable
data class CharacterFormDestination(
    val characterId: Int? = null
)

fun NavController.navigateToCharacterForm(characterId: Int? = null) {
    navigate(CharacterFormDestination(characterId))
}

fun NavGraphBuilder.characterFormScreen(
    onBack: () -> Unit
) {
    composable<CharacterFormDestination> {
        CharacterFormRoute(onBack = onBack)
    }
}

@Composable
fun CharacterFormRoute(
    onBack: () -> Unit,
    viewModel: CharacterFormVM = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.saved) {
        if (state.saved) {
            onBack()
        }
    }

    CharacterForm(
        state = state,
        onCharacterChange = viewModel::updateForm,
        onSave = viewModel::save,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterForm(
    state: CharacterFormState,
    onCharacterChange: (Character) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(
            title = {
                Text(
                    if (state.isEditing) {
                        "Editar personaje"
                    } else {
                        "Agregar personaje"
                    }
                )
            },
            navigationIcon = {
                TextButton(
                    onClick = onBack,
                    enabled = !state.isSaving
                ) {
                    Text("Volver")
                }
            }
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val character = state.character

                OutlinedTextField(
                    value = character.name,
                    onValueChange = {
                        onCharacterChange(character.copy(name = it))
                    },
                    label = { Text("Nombre") },
                    singleLine = true,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = character.status,
                    onValueChange = {
                        onCharacterChange(character.copy(status = it))
                    },
                    label = { Text("Estado") },
                    singleLine = true,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = character.species,
                    onValueChange = {
                        onCharacterChange(character.copy(species = it))
                    },
                    label = { Text("Especie") },
                    singleLine = true,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = character.gender,
                    onValueChange = {
                        onCharacterChange(character.copy(gender = it))
                    },
                    label = { Text("Género") },
                    singleLine = true,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = character.image,
                    onValueChange = {
                        onCharacterChange(character.copy(image = it))
                    },
                    label = { Text("URL de imagen") },
                    singleLine = true,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onSave,
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        if (state.isSaving) {
                            "Guardando..."
                        } else {
                            "Guardar personaje"
                        }
                    )
                }
            }
        }
    }
}