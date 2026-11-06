package plat.lab.applaboratorio.locations.network

import plat.lab.applaboratorio.locations.data.LocationDTO

interface LocationApi {
    suspend fun getLocations(): List<LocationDTO>
}