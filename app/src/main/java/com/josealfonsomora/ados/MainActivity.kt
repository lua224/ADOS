@file:OptIn(ExperimentalMaterial3Api::class)

package com.josealfonsomora.ados

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.room.Entity
import com.josealfonsomora.ados.data.room.AdosDatabaseRoom
import com.josealfonsomora.ados.data.sqlite.AdosDatabaseSqlite
import com.josealfonsomora.ados.domain.Autobus
import com.josealfonsomora.ados.ui.chucknorrisjokes.ChuckNorrisJoke
import com.josealfonsomora.ados.ui.main.MainScreen
import com.josealfonsomora.ados.ui.theme.ADOSTheme
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ADOSTheme {
                val scrollState = rememberScrollState()
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
                        Column(
                            modifier = Modifier.verticalScroll(scrollState)
                        ) {
                            MainScreen()
                            ChuckNorrisJoke()
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: The activity is now visible to the user.")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: The activity is now in the foreground and interactive.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: The activity is being paused; it is no longer in the foreground.")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: The activity is no longer visible to the user.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: The activity is being destroyed.")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart: The activity is restarting after being stopped.")
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Log.d(TAG, "onLowMemory: The system is running low on memory. $level")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                Log.d(TAG, "Screen Rotation: The screen is now in landscape mode.")
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                Log.d(TAG, "Screen Rotation: The screen is now in portrait mode.")
            }
            else -> {
                Log.d(TAG, "Screen Rotation: The screen orientation has changed to an undefined mode.")
            }
        }
    }

}

