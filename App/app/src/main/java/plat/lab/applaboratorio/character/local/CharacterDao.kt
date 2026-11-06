package plat.lab.applaboratorio.character.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCharacters(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characterentity")
    fun getCharacters(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM characterentity WHERE id = :id")
    suspend fun getCharacterById(id: Int): CharacterEntity

    @Query("SELECT * FROM characterentity WHERE isFavorite = 1")
    fun getFavoriteCharacters(): Flow<List<CharacterEntity>>

    @Query("UPDATE characterentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Update
    suspend fun updateCharacter(character: CharacterEntity)

    @Query("DELETE FROM characterentity")
    suspend fun deleteAllCharacters()

    @Query("DELETE FROM characterentity WHERE id = :id")
    suspend fun deleteCharacterById(id: Int)
}