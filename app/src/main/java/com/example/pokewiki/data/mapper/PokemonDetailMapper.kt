package com.example.pokewiki.data.mapper

import com.example.pokewiki.domain.model.PokemonDetailEntity
import com.example.pokewiki.domain.model.StatEntity

fun com.example.pokewiki.data.models.pokemon_detail.PokemonDetail.toPokemonDetailEntity():PokemonDetailEntity{
    return PokemonDetailEntity(
        height = this.height,
        weight = this.weight,
        id = this.id,
        name = this.name,
        sprites = this.sprites.front_default,
        stats = this.stats.map { it.toStatEntity() },
        types = this.types.map {
            it.type.name
        }
    )
}

fun com.example.pokewiki.data.models.pokemon_detail.Stat.toStatEntity(): StatEntity {
    return StatEntity(
        baseStat = this.base_stat,
        effort = this.effort,
        statName = this.stat.name

    )
}