package plat.lab.applaboratorio.locations.VM.LocationList

sealed interface LocationScreenEvent {
    data object onLoadingScreen : LocationScreenEvent
    data object onRetry : LocationScreenEvent
}