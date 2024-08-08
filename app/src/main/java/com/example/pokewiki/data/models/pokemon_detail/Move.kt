package com.example.pokewiki.data.models.pokemon_detail

data class Move(
    val move: com.example.pokewiki.data.models.pokemon_detail.MoveX,
    val version_group_details: List<com.example.pokewiki.data.models.pokemon_detail.VersionGroupDetail>
)