package plat.lab.applaboratorio.character.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import plat.lab.applaboratorio.Character

@Dao
interface CharacterDao {
    @Insert
    suspend fun addCharacters(characters: List<Character>)

    @Query("SELECT * FROM characterentity")
    suspend fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterentity WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * FROM characterentity WHERE isFavorite = 1")
    suspend fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    @Query("UPDATE characterentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}