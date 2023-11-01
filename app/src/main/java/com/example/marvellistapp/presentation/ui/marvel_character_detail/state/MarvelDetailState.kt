package com.example.marvellistapp.presentation.ui.marvel_character_detail.state

import com.example.marvellistapp.domain.model.Comics
import com.example.marvellistapp.domain.model.MarvelCharacter

data class MarvelDetailState(
    val isLoading: Boolean = false,
    val character: MarvelCharacter? = null,
    val comics: List<Comics> = emptyList(),
    val errorShowing: Boolean = false,
    val error: String = ""
)
