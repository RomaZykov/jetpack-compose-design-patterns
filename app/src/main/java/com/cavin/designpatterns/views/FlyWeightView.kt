package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.flyweight.IconFactory

@Composable
fun FlyWeightView() {
    val posts = remember {
        listOf(
            "Exploring the Flyweight Pattern in Jetpack Compose!",
            "Happy to share my latest project—excited for your feedback! 🎉",
            "Just finished a great coding session, feeling productive! 💻✨",
            "Check out this beautiful sunset I captured today! 🌅",
            "Learning Jetpack Compose has been a game changer. 🚀",
            "Enjoying a cozy day with some coffee and code ☕👩‍💻",
            "Remember: Consistency is key to growth! 💪",
            "What are your thoughts on declarative UI? Let's discuss! 📢",
            "Can't wait to deploy the new features next week! 🔥",
            "Embracing challenges—every day is a learning opportunity. 🌟"
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        posts.forEach { post ->
            PostItem(postContent = post)
        }
    }
}


@Composable
fun PostItem(postContent: String) {
    val likeIcon = IconFactory.getIcon(Icons.Default.Favorite)
    val shareIcon = IconFactory.getIcon(Icons.Default.Share)

    Card (modifier = Modifier.padding(12.dp)){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = postContent, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                likeIcon.render(color = Color.Red, size = 24.dp)
                shareIcon.render(color = Color.Blue, size = 24.dp)
            }
        }
    }
}