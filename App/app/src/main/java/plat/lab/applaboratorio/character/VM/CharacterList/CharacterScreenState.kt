package plat.lab.applaboratorio.character.VM.CharacterList

import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.CharacterDb

data class CharacterScreenState(
    val isLoading: Boolean = true,
    val data: List<Character> = CharacterDb().getAllCharacters(),
    val hasError: Boolean = false
)