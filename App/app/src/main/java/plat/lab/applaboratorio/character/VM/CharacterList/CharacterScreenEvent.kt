package plat.lab.applaboratorio.character.VM.CharacterList

sealed interface CharacterScreenEvent {
    data object onLoadingScreen : CharacterScreenEvent
    data object onRetry : CharacterScreenEvent
}