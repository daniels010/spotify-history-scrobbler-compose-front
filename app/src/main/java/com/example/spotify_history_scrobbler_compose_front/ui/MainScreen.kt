package com.example.spotify_history_scrobbler_compose_front.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.spotify_history_scrobbler_compose_front.data.models.Ranking
import com.example.spotify_history_scrobbler_compose_front.viewmodels.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel) {
    val data by viewModel.data.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isIncrementalLoading by viewModel.isIncrementalLoading.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (isLoading && data.isEmpty()) {
            // Exibir indicador de carregamento inicial
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(data) { item ->
                    RankingItem(item)
                }
                item {
                    if (isIncrementalLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Button(
                            onClick = { viewModel.loadMore() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Load More")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RankingItem(item: Ranking) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberImagePainter(data = item.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.name1,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Reproductions: ${item.reproductions}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
