package plat.lab.applaboratorio.character.domain

import kotlinx.coroutines.flow.Flow
import plat.lab.applaboratorio.Character

interface CharacterRepository {
    suspend fun insertCharacter(character: Character)
    suspend fun updateFavorite(character: Character)
    suspend fun updateCharacter(character: Character)
    suspend fun deleteCharacter(character: Character)
    suspend fun deleteAllCharacters()
    suspend fun downloadCharacters()
    fun getCharacters(): Flow<List<Character>>
    fun getFavoriteCharacters(): Flow<List<Character>>
    suspend fun getCharacterById(id: Int): Character
}