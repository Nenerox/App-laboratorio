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
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailEvent
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import plat.lab.applaboratorio.character.data.mapToModel
import plat.lab.applaboratorio.character.ui.details.CharacterDetailsDestination
import plat.lab.applaboratorio.core.data.Dependencias
import kotlin.time.Duration.Companion.seconds

class CharacterDetailsVM(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val currentId =
        savedStateHandle.toRoute<CharacterDetailsDestination>().Id
    private val _state = MutableStateFlow(CharacterDetailScreenState())
    val state = _state.asStateFlow()

    private val characterDao = Dependencias.database.characterDao()
    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            CharacterDetailEvent.onRetry -> retry()
            CharacterDetailEvent.onLoadingScreen -> loading()
            is CharacterDetailEvent.onFavorite -> {
                viewModelScope.launch {
                    characterDao.updateFavorite(event.id, event.isFavorite)
                }
            }
        }
    }

    private fun loadCharacter(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val entity = characterDao.getCharacterById(currentId)
            _state.update {
                it.copy(data = entity.mapToModel())
            }
        }
    }

    private fun retry(){
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false)}
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
