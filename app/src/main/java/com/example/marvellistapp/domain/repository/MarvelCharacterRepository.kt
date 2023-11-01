package com.example.marvellistapp.domain.repository

import androidx.paging.PagingData
import com.example.marvellistapp.data.remote.model.comics.CharacterComics
import com.example.marvellistapp.data.remote.model.dto.Result
import com.example.marvellistapp.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface MarvelCharacterRepository {
    fun getMarvelCharacters(): Flow<PagingData<MarvelCharacter>>
    suspend fun getMarvelCharacterById(characterId: Int): Result
    suspend fun getMarvelCharacterComics(characterId: Int): CharacterComics
}