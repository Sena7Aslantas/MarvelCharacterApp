package com.example.marvellistapp.presentation.ui.marvel_character_detail.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvellistapp.common.Constants
import com.example.marvellistapp.domain.repository.MarvelCharacterRepository
import com.example.marvellistapp.data.remote.model.comics.toComics
import com.example.marvellistapp.data.remote.model.dto.toMarvelCharacter
import com.example.marvellistapp.presentation.ui.marvel_character_detail.state.MarvelDetailState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MarvelCharacterDetailViewModel @Inject constructor(
    private val repository: MarvelCharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _marvelCharacter: MutableLiveData<MarvelDetailState> = MutableLiveData()
    val marvelCharacter get() = _marvelCharacter

    private val characterId = savedStateHandle.get<Int>(Constants.QUERY_CHARACTER_ID)

    init {
        viewModelScope.launch {
            getMarvelCharacterByIdAndComicsData()
        }
    }

    suspend fun getMarvelCharacterByIdAndComicsData() {
        characterId?.let { getMarvelCharacterByIdAndComics(it) }
    }

    private suspend fun getMarvelCharacterByIdAndComics(characterId: Int) {
        _marvelCharacter.value = MarvelDetailState(isLoading = true)
        try {
            val responseCharacter =
                repository.getMarvelCharacterById(characterId).data.results[0].toMarvelCharacter()
            val responseComics =
                repository.getMarvelCharacterComics(characterId).data.results.map { it.toComics() }
            _marvelCharacter.value =
                MarvelDetailState(character = responseCharacter, comics = responseComics)
        } catch (e: HttpException) {
            _marvelCharacter.value =
                MarvelDetailState(
                    error = e.localizedMessage ?: "An unexpected error occurred",
                    errorShowing = true
                )
        } catch (e: IOException) {
            _marvelCharacter.value =
                MarvelDetailState(
                    error = "Couldn't reach server. Check your internet connection.",
                    errorShowing = true
                )
        }
    }
}