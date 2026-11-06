package plat.lab.applaboratorio.locations.VM.LocationList

import plat.lab.applaboratorio.locations.data.Location

sealed interface LocationScreenEvent {
    data object onLoadingScreen : LocationScreenEvent
    data object onRetry : LocationScreenEvent
    data object onFavoritesPage: LocationScreenEvent
    data class onFavorite(val location: Location): LocationScreenEvent
}