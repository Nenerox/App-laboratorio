package plat.lab.applaboratorio.character.VM.CharacterDetails

interface CharacterDetailEvent {
    data object onLoadingScreen : CharacterDetailEvent
    data object onRetry : CharacterDetailEvent
}