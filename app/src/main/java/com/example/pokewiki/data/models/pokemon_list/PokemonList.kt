package com.example.pokewiki.data.models.pokemon_list

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List< Result>
)