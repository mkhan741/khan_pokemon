package com.example.khan_pokemon.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// object bedeutet diese Klasse wird nur einmal erstellt
object RetrofitInstance {

    // by lazy bedeutet Retrofit wird erst erstellt wenn es zum ersten Mal gebraucht wird
    val api: PokemonApiService by lazy {
        Retrofit.Builder()
            // die Basis URL der API
            .baseUrl("https://pokeapi.co/api/v2/")
            // Gson wandelt den JSON Response automatisch in unsere data classes um
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            // erstellt eine Implementierung von PokemonApiService
            .create(PokemonApiService::class.java)
    }
}