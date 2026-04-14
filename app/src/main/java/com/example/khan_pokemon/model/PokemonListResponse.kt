package com.example.khan_pokemon.model

// die API gibt eine Liste von Pokemon zurueck
// diese Klasse speichert die gesamte Antwort der API
data class PokemonListResponse(
    val results: List<PokemonEntry>
)

// ein einzelner Eintrag in der Liste hat nur einen Namen
data class PokemonEntry(
    val name: String
)