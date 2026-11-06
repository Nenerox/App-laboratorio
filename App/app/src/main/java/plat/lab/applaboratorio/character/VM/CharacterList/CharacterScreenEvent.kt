package plat.lab.applaboratorio.character.VM.CharacterList

import plat.lab.applaboratorio.Character

sealed interface CharacterScreenEvent {
    data object onLoadingScreen : CharacterScreenEvent
    data object onRetry : CharacterScreenEvent
    data object onFavoritesPage: CharacterScreenEvent
    data class onFavorite(val character: Character): CharacterScreenEvent
    data class onDelete(val character: Character): CharacterScreenEvent
}