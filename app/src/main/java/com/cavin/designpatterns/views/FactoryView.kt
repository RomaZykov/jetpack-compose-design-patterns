package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.factory.CardFactoryProvider
import com.cavin.designpatterns.patterns.factory.CardType
import com.cavin.designpatterns.patterns.factory.LocalCardFactory
import com.cavin.designpatterns.patterns.factory.NewsItem

@Composable
fun FactoryView() {

    val newsItems = remember {
        listOf(
            NewsItem("Title 1", "Content 1", "https://picsum.photos/200"),
            NewsItem("Title 2", "Content 2", "https://picsum.photos/100"),
            NewsItem("Title 3", "Content 3", "https://picsum.photos/300"),
            NewsItem("Title 4", "Content 4", "https://picsum.photos/400"),
        )
    }


    Scaffold { innerPadding ->
        NewsFeed(
            newsItems = newsItems,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NewsFeed(
    newsItems: List<NewsItem>,
    modifier: Modifier,
) {
    var selectedCardType by remember { mutableStateOf(CardType.SIMPLE) }

    CardFactoryProvider(cardType = selectedCardType) { // Or dynamically choose based on item type
        LazyColumn(modifier = modifier) {
            item {
                Row(modifier = Modifier.padding(8.dp)) {
                    Button(onClick = { selectedCardType = CardType.SIMPLE }) {
                        Text("Simple Card")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(onClick = { selectedCardType = CardType.RICH }) {
                        Text("Rich Card")
                    }
                }
            }
            items(newsItems) { item ->
                val cardFactory = LocalCardFactory.current
                cardFactory.CreateCard(item)
            }
        }
    }
}


