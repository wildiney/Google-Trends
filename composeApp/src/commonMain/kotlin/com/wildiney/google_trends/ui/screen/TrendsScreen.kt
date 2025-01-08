package com.wildiney.google_trends.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildiney.google_trends.domain.model.RssFeed
import com.wildiney.google_trends.domain.repository.TrendsRepositoryImpl
import com.wildiney.google_trends.ui.theme.ErrorColor
import com.wildiney.google_trends.ui.theme.PrimaryColor
import com.wildiney.google_trends.ui.theme.PrimaryVariantColor
import com.wildiney.google_trends.ui.theme.Typography
import com.wildiney.google_trends.ui.theme.onPrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreen() {
    Scaffold(
        topBar = { TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
            title = { Text("Google Trends", color = onPrimaryColor) }) }

    ) { innerPadding->

        var feed by remember { mutableStateOf<RssFeed?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        val repository = remember { TrendsRepositoryImpl() }
        val state = rememberLazyListState()

        LaunchedEffect(Unit) {
            try {
                feed = repository.getTrends()
            } finally {
                isLoading = false
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                repository.close()
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                feed != null -> {
                    LazyColumn(
                        state = state,
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(items = feed!!.items,
                            key = { index, _ -> index } // Usando o índice como chave única
                        ) { _, item ->
                            Card(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {

                                        Text(
                                            text = item.title,
                                            style = Typography.title,
                                            color = PrimaryColor
                                        )
                                        Text(
                                            text = item.traffic,
                                            style = Typography.amount,
                                            color = PrimaryVariantColor
                                        )
                                    }
//
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                            }
                        }
                    }

                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Erro ao carregar tendências",
                            style = Typography.title,
                            color = ErrorColor
                        )
                    }
                }
            }
        }
    }
}