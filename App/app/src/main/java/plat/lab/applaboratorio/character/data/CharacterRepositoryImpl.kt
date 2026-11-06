package plat.lab.applaboratorio.character.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.lab.applaboratorio.Character
import plat.lab.applaboratorio.character.domain.CharacterRepository
import plat.lab.applaboratorio.character.local.CharacterDao
import plat.lab.applaboratorio.character.local.mapToModel
import plat.lab.applaboratorio.character.network.CharacterApi
import plat.lab.applaboratorio.locations.data.Location
import plat.lab.applaboratorio.mapToEntity

class CharacterRepositoryImpl(
    private val dao: CharacterDao,
    private val api: CharacterApi,
): CharacterRepository {

    override suspend fun downloadCharacters() {
        val remoteCharacters = api.getCharacters()
        dao.addCharacters(
            remoteCharacters.map { it.mapToEntity() }
        )
    }

    override fun getCharacters(): Flow<List<Character>> {
        return dao.getCharacters().map { list ->
            list.map { entity -> entity.mapToModel() }
        }
    }

    override fun getFavoriteCharacters(): Flow<List<Character>> = flow {
        emitAll(
            dao.getFavoriteCharacters().map { list ->
                list.map { entity -> entity.mapToModel() }
            }
        )
    }

    override suspend fun getCharacterById(id: Int): Character{
        return dao.getCharacterById(id).mapToModel()
    }

    override suspend fun insertCharacter(character: Character) {
        dao.addCharacters(listOf(character.mapToEntity()))
    }

    override suspend fun updateFavorite(character: Character) {
        dao.updateFavorite(id = character.id, isFavorite = !character.isFavorite)
    }

    override suspend fun updateCharacter(character: Character) {
        dao.updateCharacter(character.mapToEntity())
    }

    override suspend fun deleteCharacter(character: Character) {
        dao.deleteCharacterById(character.id)
    }

    override suspend fun deleteAllCharacters() {
        dao.deleteAllCharacters()
    }
}