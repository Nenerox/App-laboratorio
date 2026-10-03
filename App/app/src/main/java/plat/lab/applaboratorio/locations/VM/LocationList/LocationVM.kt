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
import plat.lab.applaboratorio.locations.data.LocationDb
import kotlin.time.Duration.Companion.seconds

class LocationVM : ViewModel() {
    private val _state = MutableStateFlow(LocationScreenState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: LocationScreenEvent) {
        when (event) {
            LocationScreenEvent.onRetry -> retry()
            LocationScreenEvent.onLoadingScreen -> loading()
        }
    }

    private fun retry(){
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false, data = LocationDb().getAllLocations()) }
        startJob()
    }

    private fun loading(){
        job?.cancel()
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    private fun startJob() {
        job?.cancel()
        job = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false) }
            delay(4.seconds)
            _state.update { it.copy(isLoading = false, hasError = false) }
        }
    }
}