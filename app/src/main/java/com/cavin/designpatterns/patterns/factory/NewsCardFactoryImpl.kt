package com.cavin.designpatterns.patterns.factory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class SimpleCardFactory : NewsCardFactory {
    @Composable
    override fun CreateCard(newsItem: NewsItem) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(newsItem.title, style = MaterialTheme.typography.titleSmall)
                Text(newsItem.content, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}


class RichCardFactory : NewsCardFactory {
    @Composable
    override fun CreateCard(newsItem: NewsItem) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                newsItem.imageUrl?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(3.dp))
                    )
                }
                Text(newsItem.title, style = MaterialTheme.typography.titleSmall)
                Text(newsItem.content, style = MaterialTheme.typography.bodyMedium)

            }
        }
    }
}