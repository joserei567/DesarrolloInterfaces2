package com.example.desarrollointerfaces2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.desarrollointerfaces2.ui.theme.DesarrolloInterfaces2Theme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { DesarrolloInterfaces2Theme { PracticaApp() } }
    }
}

@Composable
fun PracticaApp() {
    var pantalla by rememberSaveable { mutableIntStateOf(0) }
    val estados = rememberSaveableStateHolder()
    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Text("DesarrolloInterfaces2", style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(16.dp))
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                FilterChip(pantalla == 0, { pantalla = 0 }, label = { Text("1. Controles") })
                FilterChip(pantalla == 1, { pantalla = 1 }, label = { Text("2. Tarjetas") })
            }
            Box(Modifier.weight(1f)) {
                estados.SaveableStateProvider(pantalla) {
                    if (pantalla == 0) EjercicioControles() else EjercicioTarjetas()
                }
            }
        }
    }
}

@Composable
fun EjercicioControles(modifier: Modifier = Modifier) {
    var mensaje by rememberSaveable { mutableStateOf("Selecciona una opción o presiona el botón") }
    var activar by rememberSaveable { mutableStateOf(false) }
    var mostrarOpciones by rememberSaveable { mutableStateOf(false) }
    var seleccion by rememberSaveable { mutableIntStateOf(-1) }
    var imagen by rememberSaveable { mutableIntStateOf(0) }
    var finCarga by rememberSaveable { mutableLongStateOf(0L) }
    var cargando by remember { mutableStateOf(false) }
    // Un nuevo clic reinicia los cinco segundos; el plazo sobrevive a una rotación.
    LaunchedEffect(finCarga) {
        val restante = finCarga - System.currentTimeMillis()
        cargando = restante > 0
        if (restante > 0) delay(restante)
        cargando = false
    }
    val imagenes = listOf(R.drawable.paisaje_montana, R.drawable.paisaje_mar, R.drawable.paisaje_bosque)
    val nombres = listOf("Montaña", "Mar", "Bosque")
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(painterResource(R.drawable.ic_controles), "Icono de controles", tint = MaterialTheme.colorScheme.primary)
            Text("Explora los controles", style = MaterialTheme.typography.titleLarge)
        }
        Text("Pulsa el botón para cambiar de imagen y mostrar el progreso durante 5 segundos.")
        Image(painterResource(imagenes[imagen]), "Paisaje: ${nombres[imagen]}",
            Modifier.fillMaxWidth().height(170.dp))
        Text("Imagen ${imagen + 1} de 3 · ${nombres[imagen]}", style = MaterialTheme.typography.labelLarge)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = {
                mensaje = "Botón presionado"
                imagen = (imagen + 1) % imagenes.size
                finCarga = System.currentTimeMillis() + 5000L
            }) { Text("Presionar") }
            if (cargando) {
                CircularProgressIndicator(Modifier.size(28.dp))
                Text("Cargando…")
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(activar, { activar = it })
            Text("Activar")
        }
        if (activar) {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium) {
                Text(mensaje, Modifier.fillMaxWidth().padding(16.dp))
            }
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text("Mostrar opciones")
            Switch(mostrarOpciones, { mostrarOpciones = it })
        }
        if (mostrarOpciones) {
            Column(Modifier.selectableGroup()) {
                nombres.forEachIndexed { indice, nombre ->
                    Row(Modifier.fillMaxWidth().selectable(seleccion == indice, role = Role.RadioButton,
                        onClick = { seleccion = indice; mensaje = "Opción seleccionada: $nombre" }).padding(vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(seleccion == indice, onClick = null)
                        Spacer(Modifier.width(12.dp))
                        Text(nombre)
                    }
                }
            }
        }
    }
}

private data class Tarea(val id: Int, val titulo: String, val detalle: String)
private val tareas = listOf(
    Tarea(1, "Repasar Jetpack Compose", "Componentes y gestión del estado"),
    Tarea(2, "Terminar la práctica", "Comprobar los controles y las tarjetas"),
    Tarea(3, "Preparar la entrega", "Revisar el proyecto en Android Studio"),
    Tarea(4, "Organizar los apuntes", "Guardar los ejemplos de clase")
)
private val prioridades = listOf("Alta", "Media", "Baja")

@Composable
fun EjercicioTarjetas() {
    var completadas by rememberSaveable { mutableStateOf(listOf(4)) }
    var niveles by rememberSaveable { mutableStateOf(listOf(0, 0, 1, 2)) }
    fun cambiarEstado(id: Int, completada: Boolean) {
        completadas = if (completada) (completadas + id).distinct() else completadas - id
    }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            Text("Mis tareas", style = MaterialTheme.typography.titleLarge)
            Text("${tareas.size - completadas.size} pendientes · ${completadas.size} completadas")
        }
        item { Text("Pendientes", style = MaterialTheme.typography.titleMedium) }
        if (completadas.size == tareas.size) item { Text("No hay tareas pendientes.") }
        items(tareas.filter { it.id !in completadas }, key = { it.id }) { tarea ->
            TarjetaTarea(tarea, false, niveles[tarea.id - 1], { cambiarEstado(tarea.id, it) }, { nivel ->
                niveles = niveles.toMutableList().also { it[tarea.id - 1] = nivel }
            })
        }
        item {
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            Text("Completadas", style = MaterialTheme.typography.titleMedium)
        }
        if (completadas.isEmpty()) item { Text("Todavía no has completado ninguna tarea.") }
        items(tareas.filter { it.id in completadas }, key = { it.id }) { tarea ->
            TarjetaTarea(tarea, true, niveles[tarea.id - 1], { cambiarEstado(tarea.id, it) }, { nivel ->
                niveles = niveles.toMutableList().also { it[tarea.id - 1] = nivel }
            })
        }
    }
}

@Composable
private fun TarjetaTarea(tarea: Tarea, completada: Boolean, prioridad: Int,
    onEstado: (Boolean) -> Unit, onPrioridad: (Int) -> Unit) {
    var menuEstado by remember { mutableStateOf(false) }
    var menuPrioridad by remember { mutableStateOf(false) }
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(completada, onEstado)
                Column(Modifier.weight(1f)) {
                    Text(tarea.titulo, style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (completada) TextDecoration.LineThrough else TextDecoration.None)
                    Text(tarea.detalle, style = MaterialTheme.typography.bodyMedium)
                }
            }
            // BadgedBox es el equivalente Material 3 de BadgeBox del enunciado.
            BadgedBox(badge = {
                Badge(containerColor = listOf(Color(0xFFB3261E), Color(0xFF795500), Color(0xFF246B39))[prioridad]) {
                    Text(prioridades[prioridad], color = Color.White)
                }
            }) { Text("Prioridad", Modifier.padding(end = 38.dp, top = 6.dp)) }
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box {
                    OutlinedButton({ menuEstado = true }) { Text("Estado ▾") }
                    DropdownMenu(menuEstado, { menuEstado = false }) {
                        DropdownMenuItem(text = { Text("Marcar como completada") }, onClick = {
                            onEstado(true); menuEstado = false
                        })
                        DropdownMenuItem(text = { Text("Marcar como pendiente") }, onClick = {
                            onEstado(false); menuEstado = false
                        })
                    }
                }
                Box {
                    OutlinedButton({ menuPrioridad = true }) { Text("Prioridad ▾") }
                    DropdownMenu(menuPrioridad, { menuPrioridad = false }) {
                        prioridades.forEachIndexed { indice, nombre ->
                            DropdownMenuItem(text = { Text(nombre) }, onClick = {
                                onPrioridad(indice); menuPrioridad = false
                            })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EjercicioControlesPreview() { DesarrolloInterfaces2Theme { PracticaApp() } }
