package com.josealfonsomora.ados.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.josealfonsomora.ados.R
import com.josealfonsomora.ados.domain.Autobus
import com.josealfonsomora.ados.ui.theme.ADOSTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    when (val current = state) {
        MainScreenState.Error -> {}
        is MainScreenState.Loaded -> {
            MainContent(current.list){ bus ->
                viewModel.deleteAutobus(bus)
            }
        }

        MainScreenState.Loading -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    autobuses: List<Autobus>,
    deleteAutobus:(Autobus)->Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("ADOS") }) }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Image(
                painter = painterResource(id = R.drawable.ados), // Replace with your local image resource name
                contentDescription = "Local JPG Image",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
            AutobusList(autobuses) {bus->
                deleteAutobus(bus)
            }

        }
    }
}

@Composable
fun AutobusList(autobuses: List<Autobus>, onDelete: (Autobus) -> Unit) {
    LazyColumn {
        items(autobuses) { autobus ->
            AutobusItem(autobus = autobus, onDelete = onDelete)
        }
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
        ){}
    }
}