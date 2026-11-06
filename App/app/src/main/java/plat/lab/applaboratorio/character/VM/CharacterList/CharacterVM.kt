package plat.lab.applaboratorio.character.VM.CharacterList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import plat.lab.applaboratorio.AppLaboratorio
import plat.lab.applaboratorio.character.local.mapToModel
import kotlin.time.Duration.Companion.seconds

class CharacterVM(application: Application): AndroidViewModel(application) {
    private val _state = MutableStateFlow(CharacterScreenState())
    val state = _state.asStateFlow()
    private val characterRepository = (application as AppLaboratorio).CharacterRepository
    private var job: Job? = null
    private var dataJob: Job? = null

    init {
        startJob()
    }

    fun onEvent(event: CharacterScreenEvent) {
        when (event) {
            CharacterScreenEvent.onRetry -> retry()
            CharacterScreenEvent.onLoadingScreen -> loading()
            CharacterScreenEvent.onFavoritesPage -> favoritesList()
            is CharacterScreenEvent.onFavorite -> {
                viewModelScope.launch {
                    characterRepository.updateFavorite(event.character)
                }
            }
            is CharacterScreenEvent.onDelete -> {
                viewModelScope.launch {
                    characterRepository.deleteCharacter(event.character)
                }
            }
        }
    }

    private fun favoritesList(){
        _state.update { it.copy(favorites = !it.favorites) }
        loadCharacters()
    }

    private fun loadCharacters() {
        dataJob?.cancel()
        dataJob = viewModelScope.launch {
            val charactersFlow = if (_state.value.favorites) {
                characterRepository.getFavoriteCharacters()
            } else {
                characterRepository.getCharacters()
            }
            charactersFlow.collect { characters ->
                _state.update { it.copy(data = characters) }
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
        loadCharacters()
        job = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, hasError = false) }
            delay(4.seconds)
            _state.update { it.copy(isLoading = false, hasError = false) }
        }
    }
}