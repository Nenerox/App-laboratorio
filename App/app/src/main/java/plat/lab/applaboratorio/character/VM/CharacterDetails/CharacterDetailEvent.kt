package plat.lab.applaboratorio.character.VM.CharacterDetails

import plat.lab.applaboratorio.Character

interface CharacterDetailEvent {
    data object onLoadingScreen : CharacterDetailEvent
    data object onRetry : CharacterDetailEvent
    data class onFavorite(val character: Character): CharacterDetailEvent
}