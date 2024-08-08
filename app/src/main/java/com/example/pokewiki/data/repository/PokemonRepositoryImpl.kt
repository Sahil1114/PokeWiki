package com.example.pokewiki.data.repository

import com.example.pokewiki.data.mapper.toPokemonDetailEntity
import com.example.pokewiki.data.mapper.toPokemonListEntity
import com.example.pokewiki.data.network.ApiService
import com.example.pokewiki.domain.model.PokemonDetailEntity
import com.example.pokewiki.domain.model.PokemonListEntity
import com.example.pokewiki.domain.repository.PokemonRepository
import com.example.pokewiki.utils.Resource
import javax.inject.Inject

class PokemonRepositoryImpl
@Inject constructor(
    private val api: ApiService
): PokemonRepository {
    override suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListEntity> {
        val response = try {
            api.getPokemonList(limit, offset).toPokemonListEntity()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured. ${e.localizedMessage}")
        }
        return Resource.Success(response)
    }

    override suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDetailEntity> {
        val response = try {
            api.getPokemonInfo(pokemonName).toPokemonDetailEntity()
        } catch(e: Exception) {
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}