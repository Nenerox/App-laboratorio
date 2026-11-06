package plat.lab.applaboratorio.locations.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import plat.lab.applaboratorio.locations.domain.LocationRepository
import plat.lab.applaboratorio.locations.local.LocationDao
import plat.lab.applaboratorio.locations.local.mapToModel
import plat.lab.applaboratorio.locations.network.LocationApi

class LocationRepositoryImpl(
    private val dao: LocationDao,
    private val api: LocationApi,
): LocationRepository {
    override suspend fun downloadLocations() {
        val remoteLocations = api.getLocations()
        dao.addLocations(
            remoteLocations.map { it.mapToEntity() }
        )
    }

    override fun getLocations(): Flow<List<Location>> {
        return dao.getLocations().map { list ->
            list.map { entity -> entity.mapToModel() }
        }
    }

    override fun getFavoriteLocations(): Flow<List<Location>> = flow {
        emitAll(
            dao.getFavoriteLocations().map { list ->
                list.map { entity -> entity.mapToModel() }
            }
        )
    }

    override suspend fun getLocationById(id: Int): Location{
        return dao.getLocationsById(id).mapToModel()
    }

    override suspend fun updateFavorite(location: Location) {
        dao.updateFavorite(id = location.id, isFavorite = !location.isFavorite)
    }

    override suspend fun deleteAllLocations() {
        dao.deleteAllLocations()
    }
}