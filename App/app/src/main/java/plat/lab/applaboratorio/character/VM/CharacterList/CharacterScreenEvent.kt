package plat.lab.applaboratorio.character.VM.CharacterList

sealed interface CharacterScreenEvent {
    data object onLoadingScreen : CharacterScreenEvent
    data object onRetry : CharacterScreenEvent
    data object onFavoritesPage: CharacterScreenEvent
    data class onFavorite(val id: Int, val isFavorite: Boolean): CharacterScreenEvent
}