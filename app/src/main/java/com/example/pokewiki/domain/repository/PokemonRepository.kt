package com.example.pokewiki.domain.repository

import com.example.pokewiki.domain.model.PokemonDetailEntity
import com.example.pokewiki.domain.model.PokemonListEntity
import com.example.pokewiki.utils.Resource

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int, offset: Int): Resource<PokemonListEntity>

    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDetailEntity>
}