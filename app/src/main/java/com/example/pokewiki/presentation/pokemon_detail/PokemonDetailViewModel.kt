package com.example.pokewiki.presentation.pokemon_detail

import androidx.lifecycle.ViewModel
import com.example.pokewiki.domain.model.PokemonDetailEntity
import com.example.pokewiki.domain.repository.PokemonRepository
import com.example.pokewiki.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel(){

    suspend fun getPokemonInfo(pokemonName:String):Resource<PokemonDetailEntity>{

        return repository.getPokemonInfo(pokemonName)
    }
}