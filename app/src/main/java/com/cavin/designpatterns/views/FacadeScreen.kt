package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.facade.AnalyticsManager
import com.cavin.designpatterns.patterns.facade.AudioPlayer
import com.cavin.designpatterns.patterns.facade.MusicPlayerFacade
import com.cavin.designpatterns.patterns.facade.NotificationManager

@Composable
fun FacadeView() {
    val musicPlayerFacade = remember {
        MusicPlayerFacade(
            audioPlayer = AudioPlayer(),
            notificationManager = NotificationManager(),
            analyticsManager = AnalyticsManager()
        )

    }

    var isPlaying by remember { mutableStateOf(false) }
    val track = "My Favorite Song"

    Scaffold {

        Column(modifier = Modifier
            .padding(16.dp)
            .padding(it)) {
            Text("Music Player", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isPlaying) {
                        musicPlayerFacade.pauseTrack()
                    } else {
                        musicPlayerFacade.playTrack(track)
                    }
                    isPlaying = !isPlaying
                }) {
                Text(if (isPlaying) "Pause" else "Play")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    musicPlayerFacade.stopTrack()
                    isPlaying = false
                }) {
                Text("Stop")
            }
        }
    }
}