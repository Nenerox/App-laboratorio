package plat.lab.applaboratorio.locations.VM.LocationDetails

import plat.lab.applaboratorio.locations.data.Location

interface LocationDetailEvent {
    data object onLoadingScreen : LocationDetailEvent
    data object onRetry : LocationDetailEvent
    data class onFavorite(val location: Location): LocationDetailEvent

}