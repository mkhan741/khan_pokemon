package com.example.khan_pokemon.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.khan_pokemon.model.PokemonDetail
import com.example.khan_pokemon.network.RetrofitInstance

@Composable
fun PokemonDetailScreen(name: String, navController: NavController) {

    // hier speichern wir das geladene Pokemon, am Anfang null weil noch nichts geladen
    var pokemon by remember { mutableStateOf<PokemonDetail?>(null) }

    // zeigt an ob die Daten noch geladen werden
    var isLoading by remember { mutableStateOf(true) }

    // wird ausgefuehrt wenn sich der Name aendert oder der Screen geoeffnet wird
    LaunchedEffect(name) {
        try {
            // Details des Pokemon laden anhand des Namens
            pokemon = RetrofitInstance.api.getPokemonDetail(name)
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

    // falls das Pokemon nicht gefunden wurde
    if (pokemon == null) {
        Text("Pokemon nicht gefunden")
        return
    }

    // ab hier ist pokemon sicher nicht null, sonst p?.name
    val p = pokemon!!

    // verticalScroll damit man scrollen kann falls der Inhalt zu lang ist
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Bild des Pokemon
        AsyncImage(
            model = p.sprites.front_default,
            contentDescription = p.name,
            modifier = Modifier.size(150.dp)
        )

        Spacer(Modifier.height(8.dp))

        // Name anzeigen
        Text(text = p.name)

        // ID des Pokemon
        Text("# ${p.id}")

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        // Typen anzeigen, mehrere Typen mit Komma trennen
        var typen = ""
        for (slot in p.types) {
            typen += slot.type.name + ", "
        }
        Text(text = "Typ: $typen")

        Spacer(Modifier.height(8.dp))

        // Groesse kommt in Dezimeter von der API rechnen auf cm um
        Text("Groesse: ${p.height * 10} cm")

        // Gewicht kommt in Hectogramm von der API rechnen auf Gramm um
        Text("Gewicht: ${p.weight * 100} g")

        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        Text("Stats", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))

        // alle Stats durchgehen und anzeigen
        p.stats.forEach { stat ->
            Text("${stat.stat.name}: ${stat.base_stat}")

            // Fortschrittsbalken, maximaler Wert ist 255
            LinearProgressIndicator(
                progress = { stat.base_stat / 255f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // zurueck Button, popBackStack geht zum vorherigen Screen zurueck
        Button(onClick = { navController.popBackStack() }) {
            Text("Zurueck")
        }
    }
}