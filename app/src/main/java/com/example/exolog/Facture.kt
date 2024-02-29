package com.example.exolog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

fun calculerTTC(MHT: Float, tauxTVA: Float, remise: Float): Float {
    return MHT * (1 + tauxTVA / 100) - remise
}

@Composable
fun Facture(navController: NavController){
// Les états pour les valeurs de calcul
    var quantite by remember { mutableStateOf("") }
    var prixunitaire by remember { mutableStateOf("") }
    var selectedOption by remember { mutableStateOf("Fidèle") }
    var remise by remember { mutableStateOf("") }

// Les états pour les valeurs calculées
    var MHT by remember { mutableStateOf(0f) }
    var ttva by remember { mutableStateOf(20f) } // Initialisation avec 20%
    var calculatedTTC by remember { mutableStateOf(0f) }


    // Calcul du montant HT lorsque les valeurs de quantite ou prixunitaire changent
    LaunchedEffect(quantite, prixunitaire) {
        val qte = quantite.toFloatOrNull() ?: 0f
        val prixuni = prixunitaire.toFloatOrNull() ?: 0f
        MHT = qte * prixuni
    }

    // Calcul du montant TTC lorsque les valeurs de MHT, ttva ou remise changent
    LaunchedEffect(MHT, ttva, remise) {
        calculatedTTC = calculerTTC(MHT, ttva, remise.toFloatOrNull() ?: 0f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Facture",
            style = TextStyle(
                fontSize = 24.sp,
                color = Color(android.graphics.Color.parseColor("#0A5A6F"))
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = quantite,
            onValueChange = { quantite = it },
            label = { Text("Quantité") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = prixunitaire,
            onValueChange = { prixunitaire = it },
            label = { Text("Prix Unitaire") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = MHT.toString(),
            onValueChange = { /* Nothing to do here */ },
            label = { Text("Montant HT") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = ttva.toString(),
            onValueChange = { /* Nothing to do here */ },
            label = { Text("Taux TVA") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = selectedOption == "Fidèle",
                onClick = { selectedOption = "Fidèle" },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
            )
            Text("Fidèle")

            RadioButton(
                selected = selectedOption == "Non Fidèle",
                onClick = { selectedOption = "Non Fidèle" },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Blue)
            )
            Text("Non Fidèle")
        }

        TextField(
            value = remise,
            onValueChange = { remise = it },
            label = { Text("Remise") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = {
                navController.navigate("Total/${calculatedTTC.toString()}")
            }) {
                Text(text = "Calculer TTC")
            }
        }
    }
}

