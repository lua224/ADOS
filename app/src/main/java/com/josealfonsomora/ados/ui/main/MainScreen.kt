package com.josealfonsomora.ados.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.josealfonsomora.ados.domain.Autobus
import com.josealfonsomora.ados.ui.theme.ADOSTheme

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    usuariosViewModel: UsuariosViewModel = hiltViewModel()
) {
    val usuariosState by usuariosViewModel.state.collectAsState()
    val mainState by mainViewModel.state.collectAsState()

    stateMostrarUsuario(usuariosState)
    botonAñadirBus()

    //no se qué pasa que no consigo que room reciba los datos del archivo, pero al darle al botón sí se guarda correctamente el bus de prueba
    when (val current = mainState) {
        MainScreenState.Error -> {}
        is MainScreenState.Loaded -> {
            MainContent(current.list) { bus ->
                mainViewModel.deleteAutobus(bus)
            }
        }
        MainScreenState.Loading -> {}
    }

    UserFormScreen(usuariosViewModel)
}

@Composable
fun stateMostrarUsuario(usuariosState: UsuariosState){
    when (val current = usuariosState) {
        UsuariosState.Error -> {}
        is UsuariosState.Loaded -> {
            mostrarUsuario(current.nombre)
        }
        UsuariosState.Loading -> {}
    }
}

@Composable
fun mostrarUsuario(usuario: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = usuario, textAlign = TextAlign.Right)
    }
}

@Composable
fun UserFormScreen(usuariosViewModel: UsuariosViewModel) {
    var usuario by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }

    UserForm(
        usuario = usuario,
        nombre = nombre,
        onUserNameChange = { usuario = it },
        onPasswordChange = { nombre = it },
        onSubmit = {
            usuariosViewModel.insertarNuevoUsuario(usuario, nombre)
        }
    )
}

@Composable
fun UserForm(
    usuario: String,
    nombre: String,
    onUserNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = usuario,
            onValueChange = onUserNameChange,
            label = { Text("Usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = nombre,
            onValueChange = onPasswordChange,
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar")
        }
    }
}

@Composable
fun botonAñadirBus(viewModel: MainViewModel = hiltViewModel()) {
    var autobus = Autobus(id = 123456789, "Calle coruña", "13/45/56", "12:00", "14:00")
    Button(onClick = { viewModel.insertAutobuses(autobus) }) {
        Text("Registrar nuevo bus")
    }
}

@Composable
private fun MainContent(
    autobuses: List<Autobus>, deleteAutobus: (Autobus) -> Unit
) {

    AutobusList(autobuses) { bus ->
        deleteAutobus(bus)
    }
}

@Composable
fun AutobusList(autobuses: List<Autobus>, onDelete: (Autobus) -> Unit) {
    autobuses.forEach { autobus ->
        AutobusItem(autobus = autobus, onDelete = onDelete)
    }
}

@Composable
fun AutobusItem(autobus: Autobus, onDelete: (Autobus) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Dirección: ${autobus.direccion}")
            Text(text = "Fecha: ${autobus.fecha}")
            Text(text = "Inicio: ${autobus.inicio}")
            Text(text = "Fin: ${autobus.fin}")
            Button(onClick = { onDelete(autobus) }) {
                Text("Eliminar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ADOSTheme {
        MainContent(
            autobuses = listOf()
        ) {}
    }
}