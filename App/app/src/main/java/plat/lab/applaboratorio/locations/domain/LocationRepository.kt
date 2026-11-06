package plat.lab.applaboratorio.locations.domain

import kotlinx.coroutines.flow.Flow
import plat.lab.applaboratorio.locations.data.Location

interface LocationRepository {
    suspend fun downloadLocations()
    suspend fun updateFavorite(location: Location)
    suspend fun deleteAllLocations()
    suspend fun getLocationById(id: Int): Location
    fun getLocations(): Flow<List<Location>>
    fun getFavoriteLocations(): Flow<List<Location>>
}