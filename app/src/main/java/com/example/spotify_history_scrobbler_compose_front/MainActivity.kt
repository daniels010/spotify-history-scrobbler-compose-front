package com.example.spotify_history_scrobbler_compose_front

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotify_history_scrobbler_compose_front.ui.MainScreen
import com.example.spotify_history_scrobbler_compose_front.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel()
            MainScreen(viewModel)
        }
    }
}
