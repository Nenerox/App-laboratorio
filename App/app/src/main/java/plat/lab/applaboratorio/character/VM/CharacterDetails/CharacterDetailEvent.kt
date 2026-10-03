package plat.lab.applaboratorio.character.VM.CharacterDetails

import plat.lab.applaboratorio.character.VM.CharacterList.CharacterScreenEvent

interface CharacterDetailEvent {
    data object onLoadingScreen : CharacterDetailEvent
    data object onRetry : CharacterDetailEvent
    data class onCharacterClick(val id: Int) : CharacterDetailEvent
}