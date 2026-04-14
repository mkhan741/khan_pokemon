package com.example.khan_pokemon.model

// alle Daten die die API fuer ein einzelnes Pokemon liefert
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val sprites: Sprites
)

// der Typ ist verschachtelt in der API, daher brauchen wir zwei Klassen
data class TypeSlot(val type: TypeInfo)
data class TypeInfo(val name: String)

// die Stats sind auch verschachtelt
data class StatSlot(
    val base_stat: Int,
    val stat: StatInfo
)
data class StatInfo(val name: String)

// sprites sind die Bilder des Pokemon
data class Sprites(val front_default: String?)