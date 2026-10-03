package plat.lab.applaboratorio.character.VM

import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.Character

data class CharacterScreenState(
    val isLoading: Boolean = true,
    val data: List<Character> = CharacterDb().getAllCharacters(),
    val hasError: Boolean = false
)