package com.example.spotify_history_scrobbler_compose_front.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import com.example.spotify_history_scrobbler_compose_front.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val api = RetrofitClient.api

    private val _data = MutableStateFlow<List<Ranking>>(emptyList())
    val data: StateFlow<List<Ranking>> = _data

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _data.value = api.fetchData()
            } catch (e: Exception) {
                // Handle exceptions
                _data.value = listOf(Ranking("Error", 0))
            }
        }
    }
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
