package com.example.pokewiki.domain.model

data class PokemonListEntity(
    val count: Int,
    val next: String,
    val previous: Any?,
    val results: List<PokemonResultEntity>
)