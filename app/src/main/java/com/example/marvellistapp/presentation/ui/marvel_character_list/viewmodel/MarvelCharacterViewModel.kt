package com.example.marvellistapp.presentation.ui.marvel_character_list.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvellistapp.domain.model.MarvelCharacter
import com.example.marvellistapp.domain.repository.MarvelCharacterRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class MarvelCharacterViewModel @Inject constructor(
    private val repository: MarvelCharacterRepository
) : ViewModel() {
    private val _marvelCharacterList: MutableLiveData<PagingData<MarvelCharacter>> =
        MutableLiveData()
    val marvelCharacterList get() = _marvelCharacterList

    init {
        viewModelScope.launch {
            getMarvelCharacters()
        }
    }

    private suspend fun getMarvelCharacters() {
        repository.getMarvelCharacters().cachedIn(viewModelScope).distinctUntilChanged()
            .collectLatest {
                _marvelCharacterList.value = it
            }
    }
}
