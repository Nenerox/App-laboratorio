package plat.lab.applaboratorio.locations.VM.LocationDetails

import plat.lab.applaboratorio.locations.data.Location

data class LocationDetailScreenState(
    val isLoading: Boolean = true,
    val data: Location? = null,
    val hasError: Boolean = false
)