package com.example.marvellistapp.di

import com.example.marvellistapp.common.Constants.BASE_URL
import com.example.marvellistapp.data.remote.network.MarvelCharacterApi
import com.example.marvellistapp.data.repository.MarvelCharacterRepositoryImpl
import com.example.marvellistapp.domain.repository.MarvelCharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): MarvelCharacterApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelCharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMarvelRepository(api: MarvelCharacterApi): MarvelCharacterRepository {
        return MarvelCharacterRepositoryImpl(api)
    }
}
