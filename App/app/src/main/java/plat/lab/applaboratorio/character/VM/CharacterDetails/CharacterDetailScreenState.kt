package plat.lab.applaboratorio.character.VM.CharacterDetails

import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.Character

data class CharacterDetailScreenState(
    val isLoading: Boolean = true,
    val data: Character? = CharacterDb().getCharacterById(1),
    val hasError: Boolean = false
)