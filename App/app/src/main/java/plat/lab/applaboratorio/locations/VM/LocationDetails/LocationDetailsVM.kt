package plat.lab.applaboratorio.character.VM.CharacterList

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.core.data.Dependencias
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailEvent
import plat.lab.applaboratorio.locations.VM.LocationDetails.LocationDetailScreenState
import plat.lab.applaboratorio.locations.data.mapToModel
import plat.lab.applaboratorio.locations.ui.details.LocationDetailsDestination
import kotlin.time.Duration.Companion.seconds

class LocationDetailsVM(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val currentId =
        savedStateHandle.toRoute<LocationDetailsDestination>().Id
    private val _state = MutableStateFlow(LocationDetailScreenState())
    val state = _state.asStateFlow()

    private val locationDao = Dependencias.database.locationDao()

    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: LocationDetailEvent) {
        when (event) {
            LocationDetailEvent.onRetry -> retry()
            LocationDetailEvent.onLoadingScreen -> loading()
        }
    }

    private fun loadCharacter(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val entity = locationDao.getLocationsById(currentId)
            _state.update {
                it.copy(data = entity.mapToModel())
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
