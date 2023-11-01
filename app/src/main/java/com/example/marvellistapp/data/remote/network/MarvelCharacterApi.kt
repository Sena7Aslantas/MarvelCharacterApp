package com.example.marvellistapp.data.remote.network
import com.example.marvellistapp.data.remote.model.dto.Result
import com.example.marvellistapp.data.remote.model.comics.CharacterComics
import com.example.marvellistapp.util.Utils
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface MarvelCharacterApi {
    @GET("/v1/public/characters")
    suspend fun getMarvelCharacters(
        @Query("offset") offset: Int,
        @QueryMap queries: Map<String, String> = Utils.applyQueries()): Result

    @GET("/v1/public/characters/{characterId}")
    suspend fun getMarvelCharacterById(
        @Path("characterId") characterId: Int,
        @QueryMap queries: Map<String, String> = Utils.applyQueries()
    ): Result

    @GET("/v1/public/characters/{characterId}/comics")
    suspend fun getMarvelCharacterComics(
        @Path("characterId") characterId: Int,
        @QueryMap queries: Map<String, String> = Utils.applyQueries()
    ): CharacterComics
}