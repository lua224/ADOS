package com.josealfonsomora.ados.ui.chucknorrisjokes

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChuckNorrisJoke(
    viewModel: ChuckNorrisJokeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    when(val current = state){
        is ChuckNorrisJokeState.Error -> {
            Text(current.message)
        }
        is ChuckNorrisJokeState.Loaded -> ChuckNorrisJokeContent(current.joke){viewModel.onCardClicked()}
        ChuckNorrisJokeState.Loading -> {}
    }
}

@Composable
fun ChuckNorrisJokeContent(
    joke:String,
    onCardClick:()->Unit
){
    Card(
        onClick = {onCardClick()}
    ) {
        Text(text = joke, textAlign = TextAlign.Center)
    }
}