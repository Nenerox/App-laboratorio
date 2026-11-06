package plat.lab.applaboratorio.character.VM.CharacterForm

import plat.lab.applaboratorio.Character

data class CharacterFormState(
    val character: Character = Character(
        id = 0,
        name = "",
        status = "",
        species = "",
        gender = "",
        image = ""
    ),
    val isEditing: Boolean = false,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val saved: Boolean = false
)