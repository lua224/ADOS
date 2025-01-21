@file:OptIn(ExperimentalMaterial3Api::class)

package com.josealfonsomora.ados

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.josealfonsomora.ados.ui.theme.ADOSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADOSTheme {
                MainContent()

            }
        }
    }

}

@Composable
private fun MainContent() {
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
            ListOfDonationPlaces()
        }
    }
}

@Composable
private fun ListOfDonationPlaces() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("List of places to donate", style = MaterialTheme.typography.titleLarge)
        val itemsList = listOf(
            "A Coruña", "Pontevedra", "Vigo", "Lugo", "Ourense"
        )
        LazyColumn(
            modifier = Modifier.padding(16.dp)
        ) {
            items(itemsList.sorted()) {
                Text(it)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ADOSTheme {
        MainContent()
    }
}