package plat.lab.applaboratorio.character.VM.CharacterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.AppLaboratorio
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailEvent
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailScreenState
import plat.lab.applaboratorio.locations.local.mapToModel
import plat.lab.applaboratorio.locations.ui.details.LocationDetailsDestination
import kotlin.time.Duration.Companion.seconds

class LocationDetailsVM(application: Application, savedStateHandle: SavedStateHandle): AndroidViewModel(application){
    private val currentId =
        savedStateHandle.toRoute<LocationDetailsDestination>().Id
    private val _state = MutableStateFlow(LocationDetailScreenState())
    val state = _state.asStateFlow()
    private val locationRepository = (application as AppLaboratorio).LocationRepository

    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: LocationDetailEvent) {
        when (event) {
            LocationDetailEvent.onRetry -> retry()
            LocationDetailEvent.onLoadingScreen -> loading()
            is LocationDetailEvent.onFavorite -> {
                viewModelScope.launch {
                    locationRepository.updateFavorite(event.location)
                }
            }
        }
    }

    private fun loadCharacter(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val location = locationRepository.getLocationById(id = currentId)
            _state.update {
                it.copy(data = location)
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
        loadCharacter()
        job = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false) }
            delay(2.seconds)
            _state.update { it.copy(isLoading = false, hasError = false) }
        }
    }

}
