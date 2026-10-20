package plat.lab.applaboratorio.locations.VM.LocationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.core.data.Dependencias
import plat.lab.applaboratorio.locations.data.LocationDb
import plat.lab.applaboratorio.locations.data.mapToModel
import kotlin.time.Duration.Companion.seconds

class LocationVM : ViewModel() {
    private val _state = MutableStateFlow(LocationScreenState())
    val state = _state.asStateFlow()

    private val locationDao = Dependencias.database.locationDao()

    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: LocationScreenEvent) {
        when (event) {
            LocationScreenEvent.onRetry -> retry()
            LocationScreenEvent.onLoadingScreen -> loading()
        }
    }

    private fun loadLocations(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val locationsFlow = locationDao.getLocations()
            locationsFlow.collect { entities ->
                _state.update {
                    it.copy(
                        data = entities.map {
                            it.mapToModel()
                        }
                    )
                }
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