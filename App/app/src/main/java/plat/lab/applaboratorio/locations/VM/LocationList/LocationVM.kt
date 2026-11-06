package plat.lab.applaboratorio.locations.VM.LocationList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.AppLaboratorio
import kotlin.time.Duration.Companion.seconds

class LocationVM(application: Application): AndroidViewModel(application){
    private val _state = MutableStateFlow(LocationScreenState())
    val state = _state.asStateFlow()
    private val locationRepository = (application as AppLaboratorio).LocationRepository
    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: LocationScreenEvent) {
        when (event) {
            LocationScreenEvent.onRetry -> retry()
            LocationScreenEvent.onLoadingScreen -> loading()
            LocationScreenEvent.onFavoritesPage -> favoritesList()
            is LocationScreenEvent.onFavorite -> {
                viewModelScope.launch {
                    locationRepository.updateFavorite(event.location)
                }
            }
        }
    }

    private fun favoritesList(){
        _state.update { it.copy(favorites = !it.favorites) }
        loadLocations()
    }

    private fun loadLocations(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val locationsFlow = if (_state.value.favorites) {
                locationRepository.getFavoriteLocations()
            } else {
                locationRepository.getLocations()
            }
            locationsFlow.collect { locations ->
                _state.update { it.copy(data = locations) }
            }
        }
    }

    private fun retry(){
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false) }
        startJob()
    }

    private fun loading(){
        job?.cancel()
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    private fun startJob() {
        job?.cancel()
        loadLocations()
        job = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false) }
            delay(4.seconds)
            _state.update { it.copy(isLoading = false, hasError = false) }
        }
    }
}