package com.example.marvellistapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvellistapp.data.remote.model.dto.toMarvelCharacter
import com.example.marvellistapp.data.remote.network.MarvelCharacterApi
import com.example.marvellistapp.domain.model.MarvelCharacter

class MarvelCharactersPagingSource (
    private val api: MarvelCharacterApi
) : PagingSource<Int, MarvelCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, MarvelCharacter>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarvelCharacter> {
        val nextPage = params.key ?: 0
        return try {
            val response = api.getMarvelCharacters(nextPage)

            val roverInfoDetail = response.data.results.map { it.toMarvelCharacter() }

            LoadResult.Page(
                data = roverInfoDetail,
                prevKey = if (nextPage == 1) null else nextPage - 30,
                nextKey = if (roverInfoDetail.isEmpty()) null else nextPage + 30
            )
        } catch (ex: Exception) {
            return LoadResult.Error(ex)
        }
    }
}