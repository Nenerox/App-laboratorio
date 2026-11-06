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
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailEvent
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import plat.lab.applaboratorio.character.local.mapToModel
import plat.lab.applaboratorio.character.ui.details.CharacterDetailsDestination
import kotlin.time.Duration.Companion.seconds

class CharacterDetailsVM(application: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(application) {
    private val currentId =
        savedStateHandle.toRoute<CharacterDetailsDestination>().Id
    private val _state = MutableStateFlow(CharacterDetailScreenState())
    val state = _state.asStateFlow()
    private val characterRepository = (application as AppLaboratorio).CharacterRepository

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
                    characterRepository.updateFavorite(event.character)
                }
            }
        }
    }

    private fun loadCharacter(){
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val character = characterRepository.getCharacterById(id = currentId)
            _state.update {
                it.copy(data = character)
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
