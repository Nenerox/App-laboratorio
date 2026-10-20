package plat.lab.applaboratorio.core.VM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import plat.lab.applaboratorio.core.DatastorePreferences
import plat.lab.applaboratorio.core.data.Dependencias
import plat.lab.applaboratorio.locations.data.LocationDb
import plat.lab.applaboratorio.locations.data.mapToEntity
import plat.lab.applaboratorio.mapToEntity

class SessionVM(application: Application): AndroidViewModel(application) {

    private val sessionPreferences = DatastorePreferences(context = application)
    private val _state = MutableStateFlow(SessionState())
    val state = _state.asStateFlow()

    val isLogged = sessionPreferences.isLoggedIn()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun logout() {
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

    fun reiniciarDatos(){
        viewModelScope.launch {
            Dependencias.database.characterDao().deleteAllCharacters()
            Dependencias.database.locationDao().deleteAllLocations()

            val characterDao = Dependencias.database.characterDao()
            val Characters = CharacterDb().getAllCharacters()

            val locationDao = Dependencias.database.locationDao()
            val locations = LocationDb().getAllLocations()

            characterDao.addCharacters(
                Characters.map {
                    it.mapToEntity()
                }
            )
            locationDao.addLocations(
                locations.map {
                    it.mapToEntity()
                }
            )
        }


    }

    init {
        viewModelScope.launch {
            Dependencias.buildDatabase(application)
            val nombre = sessionPreferences.getNombre()
            _state.value = _state.value.copy(name = nombre)
        }
    }

}