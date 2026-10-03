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
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailEvent
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import plat.lab.applaboratorio.character.ui.details.CharacterDetailsDestination
import kotlin.time.Duration.Companion.seconds

class CharacterDetailsVM(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val currentId =
        savedStateHandle.toRoute<CharacterDetailsDestination>().Id
    private val _state = MutableStateFlow(CharacterDetailScreenState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        retry()
    }

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            CharacterDetailEvent.onRetry -> retry()
            CharacterDetailEvent.onLoadingScreen -> loading()
        }
    }

    private fun retry(){
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false, data = CharacterDb().getCharacterById(currentId)) }
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
            delay(2.seconds)
            _state.update { it.copy(isLoading = false, hasError = false) }
        }
    }

}
