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
import plat.lab.applaboratorio.character.data.mapToModel
import plat.lab.applaboratorio.core.data.Dependencias
import plat.lab.applaboratorio.ui.theme.favorite
import kotlin.time.Duration.Companion.seconds

class CharacterVM : ViewModel() {
    private val _state = MutableStateFlow(CharacterScreenState())
    val state = _state.asStateFlow()

    private val characterDao = Dependencias.database.characterDao()

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
                    characterDao.updateFavorite(event.id, event.isFavorite)
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
                characterDao.getFavoriteCharacters()
            } else {
                characterDao.getCharacters()
            }
            charactersFlow.collect { entities ->
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