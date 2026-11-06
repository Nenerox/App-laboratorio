package plat.lab.applaboratorio.locations.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLocations(locations: List<LocationEntity>)

    @Query("SELECT * FROM locationentity")
    fun getLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locationentity WHERE id = :id")
    suspend fun getLocationsById(id: Int): LocationEntity

    @Query("SELECT * FROM locationentity WHERE isFavorite = 1")
    fun getFavoriteLocations(): Flow<List<LocationEntity>>

    @Query("UPDATE locationentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)

    @Query("DELETE FROM locationentity")
    suspend fun deleteAllLocations()
}