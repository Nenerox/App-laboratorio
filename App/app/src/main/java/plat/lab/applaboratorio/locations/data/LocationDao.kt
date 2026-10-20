package plat.lab.applaboratorio.locations.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert
    suspend fun addLocations(locations: List<LocationEntity>)

    @Query("SELECT * FROM locationentity")
    fun getLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locationentity WHERE id = :id")
    suspend fun getLocationsById(id: Int): LocationEntity

    @Query("DELETE FROM locationentity")
    suspend fun deleteAllLocations()
}