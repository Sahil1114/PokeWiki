package com.example.pokewiki.data.models.pokemon_detail

data class VersionGroupDetail(
    val level_learned_at: Int,
    val move_learn_method: com.example.pokewiki.data.models.pokemon_detail.MoveLearnMethod,
    val version_group: com.example.pokewiki.data.models.pokemon_detail.VersionGroup
)