package plat.lab.applaboratorio.core.VM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.AppLaboratorio
import plat.lab.applaboratorio.character.data.CharacterDb
import plat.lab.applaboratorio.core.DatastorePreferences
import plat.lab.applaboratorio.locations.data.LocationDb
import plat.lab.applaboratorio.locations.data.mapToEntity
import plat.lab.applaboratorio.mapToEntity

class SessionVM(application: Application): AndroidViewModel(application) {

    private val sessionPreferences = DatastorePreferences(context = application)
    private val _state = MutableStateFlow(SessionState())
    val state = _state.asStateFlow()

    private val characterRepository = (application as AppLaboratorio).CharacterRepository
    private val locationRepository = (application as AppLaboratorio).LocationRepository

    val isLogged = sessionPreferences.isLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun logout() {
        borrarDatos()
        viewModelScope.launch {
            sessionPreferences.clearSession()
        }
    }

    fun saveNombre(newName: String) {
        viewModelScope.launch {
            sessionPreferences.saveNombre(newName)
            _state.value = _state.value.copy(name = newName)
        }
    }

    fun cargarDatos(){
        viewModelScope.launch {
            characterRepository.downloadCharacters()
            locationRepository.downloadLocations()
        }
    }

    private fun borrarDatos(){
        viewModelScope.launch {
            characterRepository.deleteAllCharacters()
            locationRepository.deleteAllLocations()
        }
    }

    init {
        viewModelScope.launch {
            val nombre = sessionPreferences.getNombre()
            _state.value = _state.value.copy(name = nombre)
        }
    }

}