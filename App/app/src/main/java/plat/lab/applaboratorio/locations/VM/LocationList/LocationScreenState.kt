package plat.lab.applaboratorio.locations.VM.LocationList

import plat.lab.applaboratorio.locations.data.Location
import plat.lab.applaboratorio.locations.data.LocationDb

data class LocationScreenState(
    val isLoading: Boolean = true,
    val data: List<Location> = LocationDb().getAllLocations(),
    val hasError: Boolean = false
)