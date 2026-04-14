package com.example.khan_pokemon.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.khan_pokemon.model.PokemonEntry
import com.example.khan_pokemon.network.RetrofitInstance

@Composable
fun PokemonListScreen(navController: NavController) {

    // liste der Pokemon, am Anfang leer
    var pokemonList by remember { mutableStateOf<List<PokemonEntry>>(emptyList()) }

    // zeigt an ob die Daten noch geladen werden
    var isLoading by remember { mutableStateOf(true) }

    // wird einmal ausgefuehrt wenn der Screen geöffnet wird
    LaunchedEffect(Unit) {
        try {
            // API aufrufen und Liste speichern
            pokemonList = RetrofitInstance.api.getPokemonList().results
        } catch (e: Exception) {
            // falls ein Fehler passiert ignorieren
        }
        // laden ist fertig
        isLoading = false
    }

    // solange geladen wird Ladekreis zeigen
    if (isLoading) {
        CircularProgressIndicator()
        return
    }

    // Liste anzeigen, LazyColumn rendert nur was gerade sichtbar ist
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = pokemonList, key = { it.name }) { pokemon ->

            // index berechnen fuer das Bild, API zaehlt ab 1
            val index = pokemonList.indexOf(pokemon) + 1

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    // bei Klick navigieren zur Detailseite und uebergeben den Namen
                    .clickable { navController.navigate("detail/${pokemon.name}") }
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Bild des Pokemon laden
                AsyncImage(
                    model = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index}.png",
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(56.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                // Name des Pokemon anzeigen
                Text(text = pokemon.name)
            }
            HorizontalDivider()
        }
    }
}