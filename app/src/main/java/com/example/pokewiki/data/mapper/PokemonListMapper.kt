package com.example.pokewiki.data.mapper

import com.example.pokewiki.data.models.pokemon_list.PokemonList
import com.example.pokewiki.domain.model.PokemonListEntity
import com.example.pokewiki.domain.model.PokemonResultEntity

fun PokemonList.toPokemonListEntity():PokemonListEntity{
    return PokemonListEntity(
        count=this.count,
        next=this.next,
        previous=this.previous,
        results = this.results.map {
            it.toPokemonListEntity()
        }
    )
}

fun com.example.pokewiki.data.models.pokemon_list.Result.toPokemonListEntity():PokemonResultEntity{
    val number=if (this.url.endsWith("/")){
        this.url.dropLast(1).takeLastWhile { it.isDigit() }
    }else{
        this.url.takeLastWhile { it.isDigit() }
    }
    return PokemonResultEntity(
        name=this.name,
        id = number
    )
}