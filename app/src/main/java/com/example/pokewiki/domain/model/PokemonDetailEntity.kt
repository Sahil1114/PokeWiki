package com.example.pokewiki.domain.model

data class PokemonDetailEntity (
    val height: Int,
    val id: Int,
    val name: String,
    val sprites: String,
    val stats: List<StatEntity>,
    val types: List<String>,
    val weight: Int
)