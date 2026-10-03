package plat.lab.applaboratorio.character.VM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.CharacterDb
import kotlin.time.Duration.Companion.seconds


class CharacterVM : ViewModel() {
    private val _state = MutableStateFlow(CharacterScreenState())
    val state = _state.asStateFlow()

    private var job: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: CharacterScreenEvent) {
        when (event) {
            CharacterScreenEvent.onRetry -> retry()
            CharacterScreenEvent.onLoadingScreen -> loading()
        }
    }

    private fun retry(){
        job?.cancel()
        _state.update { it.copy(isLoading = true, hasError = false, data = CharacterDb().getAllCharacters()) }
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