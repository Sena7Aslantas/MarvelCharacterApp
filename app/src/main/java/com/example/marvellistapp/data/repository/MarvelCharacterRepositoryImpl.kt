package com.example.marvellistapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvellistapp.data.paging.MarvelCharactersPagingSource
import com.example.marvellistapp.data.remote.model.comics.CharacterComics
import com.example.marvellistapp.data.remote.model.dto.Result
import com.example.marvellistapp.data.remote.network.MarvelCharacterApi
import com.example.marvellistapp.domain.model.MarvelCharacter
import com.example.marvellistapp.domain.repository.MarvelCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarvelCharacterRepositoryImpl @Inject constructor(
    private val api: MarvelCharacterApi
) : MarvelCharacterRepository {
    override fun getMarvelCharacters(): Flow<PagingData<MarvelCharacter>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 1),
            pagingSourceFactory = {
                MarvelCharactersPagingSource(api)
            }
        ).flow
    }

    override suspend fun getMarvelCharacterById(characterId: Int)=
        api.getMarvelCharacterById(characterId)

    override suspend fun getMarvelCharacterComics(
        characterId: Int
    ) = api.getMarvelCharacterComics(characterId)
}