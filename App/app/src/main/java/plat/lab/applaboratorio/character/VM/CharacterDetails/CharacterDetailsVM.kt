package plat.lab.applaboratorio.character.VM.CharacterList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.CharacterDb
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailEvent
import plat.lab.applaboratorio.character.VM.CharacterDetails.CharacterDetailScreenState
import kotlin.time.Duration.Companion.seconds

class CharacterDetailsVM : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailScreenState())
    val state = _state.asStateFlow()

    private var job: Job? = null
    private var currentId: Int? = null

    fun onEvent(event: CharacterDetailEvent) {
        when (event) {
            CharacterDetailEvent.onRetry -> retry()
            CharacterDetailEvent.onLoadingScreen -> loading()
            is CharacterDetailEvent.onCharacterClick -> characterClick(event.id)
        }
    }
    private fun retry(){
        val id = currentId ?: return
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false, data = CharacterDb().getCharacterById(id)) }
        startJob()
    }

    private fun loading(){
        job?.cancel()
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    private fun characterClick(id: Int) {
        if (currentId == id) return
        currentId = id
        retry()
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
