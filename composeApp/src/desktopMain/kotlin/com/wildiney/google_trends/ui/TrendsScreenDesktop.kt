package com.wildiney.google_trends.ui

import androidx.compose.foundation.VerticalScrollbar
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wildiney.google_trends.models.RssFeed
import com.wildiney.google_trends.repository.TrendsRepository
import com.wildiney.google_trends.ui.theme.PrimaryColor
import com.wildiney.google_trends.ui.theme.PrimaryVariantColor
import com.wildiney.google_trends.ui.theme.Typography
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import com.wildiney.google_trends.ui.theme.ErrorColor
import com.wildiney.google_trends.ui.theme.onPrimaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreenDesktop() {
    Scaffold(
        topBar = { TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor),
            title = { Text("Google Trends", color = onPrimaryColor) }) }

    ) { innerPadding->

        var feed by remember { mutableStateOf<RssFeed?>(null) }
        var isLoading by remember { mutableStateOf(true) }
        val repository = remember { TrendsRepository() }
        val state = rememberLazyListState()

        LaunchedEffect(Unit) {
            try {
                feed = repository.getTrends()
            } finally {
                isLoading = false
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
//                            .padding(end = 16.dp),
                        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        itemsIndexed(items = feed!!.items,
                            key = { index, _ -> index } // Usando o índice como chave única
                        ) { _, item ->
                            Card(
                                modifier = Modifier.fillMaxWidth().height(50.dp)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
                                    verticalArrangement = Arrangement.Center

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
//                    VerticalScrollbar(
//                        adapter = androidx.compose.foundation.rememberScrollbarAdapter(state),
//                        modifier = Modifier.align(Alignment.CenterEnd).width(12.dp)
//                    )
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