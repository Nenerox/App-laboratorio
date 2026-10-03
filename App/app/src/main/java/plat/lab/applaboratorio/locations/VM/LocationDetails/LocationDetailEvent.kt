package plat.lab.applaboratorio.locations.VM.LocationDetails

interface LocationDetailEvent {
    data object onLoadingScreen : LocationDetailEvent
    data object onRetry : LocationDetailEvent
}