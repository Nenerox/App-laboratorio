package plat.lab.applaboratorio.character.VM.CharacterList

import plat.lab.applaboratorio.Character

data class CharacterScreenState(
    val isLoading: Boolean = true,
    val data: List<Character> = emptyList(),
    val hasError: Boolean = false,
    val favorites: Boolean = false
)