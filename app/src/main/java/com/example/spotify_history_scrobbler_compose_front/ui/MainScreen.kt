package com.example.spotify_history_scrobbler_compose_front.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.spotify_history_scrobbler_compose_front.viewmodels.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val data by viewModel.data.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (data.isEmpty()) {
            Text("Loading...", modifier = Modifier.padding(16.dp))
        } else {
            Column(modifier = Modifier.padding(16.dp)) {
                data.forEach { item ->
                    Text(text = "${item.name}: ${item.reproductions}")
                }
            }
        }
    }
}
