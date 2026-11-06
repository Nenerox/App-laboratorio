package plat.lab.applaboratorio.character.network

import plat.lab.applaboratorio.character.data.CharacterDTO

interface CharacterApi {
    suspend fun getCharacters(): List<CharacterDTO>
}