package com.example.pokewiki.data.models.pokemon_detail

data class HeldItem(
    val item: com.example.pokewiki.data.models.pokemon_detail.Item,
    val version_details: List<com.example.pokewiki.data.models.pokemon_detail.VersionDetail>
)