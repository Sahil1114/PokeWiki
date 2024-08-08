package com.example.pokewiki.data.models.pokemon_detail

data class PokemonDetail(
    val abilities: List<com.example.pokewiki.data.models.pokemon_detail.Ability>,
    val base_experience: Int,
    val cries: com.example.pokewiki.data.models.pokemon_detail.Cries,
    val forms: List<com.example.pokewiki.data.models.pokemon_detail.Form>,
    val game_indices: List<com.example.pokewiki.data.models.pokemon_detail.GameIndice>,
    val height: Int,
    val held_items: List<com.example.pokewiki.data.models.pokemon_detail.HeldItem>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<com.example.pokewiki.data.models.pokemon_detail.Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<Any>,
    val past_types: List<Any>,
    val species: com.example.pokewiki.data.models.pokemon_detail.Species,
    val sprites: com.example.pokewiki.data.models.pokemon_detail.Sprites,
    val stats: List<com.example.pokewiki.data.models.pokemon_detail.Stat>,
    val types: List<com.example.pokewiki.data.models.pokemon_detail.Type>,
    val weight: Int
)