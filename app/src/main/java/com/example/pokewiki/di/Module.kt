package com.example.pokewiki.di

import com.example.pokewiki.data.network.ApiService
import com.example.pokewiki.data.repository.PokemonRepositoryImpl
import com.example.pokewiki.domain.repository.PokemonRepository
import com.example.pokewiki.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Singleton
    @Provides
    fun provideApiService():ApiService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepo(
        apiService: ApiService
    ): PokemonRepository {
        return PokemonRepositoryImpl(apiService)
    }
}