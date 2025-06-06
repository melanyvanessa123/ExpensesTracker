package com.hsbcad.gestorgastosapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.hsbcad.gestorgastosapp.ui.theme.GestorGastosAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestorGastosAppTheme {
                GastoForm()
            }
        }
    }
}

@Composable
fun GastoForm() {
    var descripcion by remember { mutableStateOf("") }
    var monto by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("Registrar Gasto", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") }
        )

        OutlinedTextField(
            value = monto,
            onValueChange = { monto = it },
            label = { Text("Monto") }
        )

        Button(onClick = {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    guardarGastoEnSupabase(descripcion, monto.toDoubleOrNull() ?: 0.0)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Gasto guardado correctamente", Toast.LENGTH_SHORT).show()
                        descripcion = ""
                        monto = ""
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al guardar gasto: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }) {
            Text("Guardar Gasto")
        }
    }
}

@Serializable
data class Gasto(
    val description: String,
    val amount: Double
)

suspend fun guardarGastoEnSupabase(descripcion: String, monto: Double) {
    val url = "https://zmatbtpcckhpckdybucl.supabase.co/rest/v1/expenses"
    val apiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InptYXRidHBjY2tocGNrZHlidWNsIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0OTEzODk2OCwiZXhwIjoyMDY0NzE0OTY4fQ.1rCdUM68BP3V-gJxk9SiBA2lcG3J0ieY1NE5QemVtw0"

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    client.post(url) {
        contentType(ContentType.Application.Json)
        header("apikey", apiKey)
        header("Authorization", "Bearer $apiKey")
        header("Prefer", "return=representation")
        setBody(Gasto(descripcion,  monto))
    }
}
