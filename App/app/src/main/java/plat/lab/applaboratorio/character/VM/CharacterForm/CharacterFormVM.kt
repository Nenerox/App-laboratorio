package plat.lab.applaboratorio.character.VM.CharacterForm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.AppLaboratorio
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.character.ui.CharacterForm.CharacterFormDestination

class CharacterFormVM(
    application: Application,
    savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private val repository = (application as AppLaboratorio).CharacterRepository

    private val characterId = savedStateHandle.toRoute<CharacterFormDestination>().characterId

    private val _state = MutableStateFlow(
        CharacterFormState(isEditing = characterId != null, isLoading = characterId != null)
    )

    val state = _state.asStateFlow()

    init {
        characterId?.let { id ->
            viewModelScope.launch {
                val character = repository.getCharacterById(id)
                _state.update {
                    it.copy(
                        character = character,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateForm(character: Character) {
        _state.update {
            it.copy(character = character)
        }
    }

    fun save() {
        val current = _state.value

        if (current.isLoading || current.isSaving || current.saved) {
            return
        }

        _state.update {
            it.copy(isSaving = true)
        }

        viewModelScope.launch {
            if (characterId != null) {
                repository.updateCharacter(current.character)
            } else {
                val characters = repository.getCharacters().first()
                val minimumId = characters.minOfOrNull { it.id } ?: 0
                val newId = minOf(0, minimumId) - 1

                repository.insertCharacter(current.character.copy(id = newId))
            }
            _state.update {
                it.copy(
                    isSaving = false,
                    saved = true
                )
            }
        }
    }
}