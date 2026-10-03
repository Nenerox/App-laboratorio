package plat.lab.applaboratorio.character.VM

sealed interface CharacterScreenEvent {
    data object onLoadingScreen : CharacterScreenEvent
    data object onRetry : CharacterScreenEvent
}