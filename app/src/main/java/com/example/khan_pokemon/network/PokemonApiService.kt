package com.example.khan_pokemon.network

import com.example.khan_pokemon.model.PokemonDetail
import com.example.khan_pokemon.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// hier definieren wir welche API Endpoints wir verwenden
interface PokemonApiService {
    // laedt eine Liste von Pokemon, limit bestimmt wie viele
    // ergibt die URL: /pokemon?limit=151
    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit: Int = 151): PokemonListResponse

    // laedt die Details eines einzelnen Pokemon anhand des Namens
    // ergibt zum Beispiel die URL: /pokemon/pikachu
    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(@Path("name") name: String): PokemonDetail
}