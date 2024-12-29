package com.cavin.designpatterns.views

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.bridge.AmazonPrimeVideo
import com.cavin.designpatterns.patterns.bridge.HDProcessor
import com.cavin.designpatterns.patterns.bridge.NetflixVideo
import com.cavin.designpatterns.patterns.bridge.QUHD8KProcessor
import com.cavin.designpatterns.patterns.bridge.UHD4KProcessor
import com.cavin.designpatterns.patterns.bridge.YoutubeVideo

@Composable
fun BridgeView() {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val youtubeVideo = YoutubeVideo(HDProcessor())
                youtubeVideo.play("video_hd.mp4")
                Toast.makeText(context, "Playing HD video on YouTube", Toast.LENGTH_SHORT).show()
            }) {
            Text("Watch HD Video on YouTube")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val netflixVideo = NetflixVideo(UHD4KProcessor())
                netflixVideo.play("video_4k.mp4")
                Toast.makeText(context, "Playing UHD 4K video on Netflix", Toast.LENGTH_SHORT)
                    .show()
            }) {
            Text("Watch UHD 4K Video on Netflix")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amazonPrimeVideo = AmazonPrimeVideo(QUHD8KProcessor())
                amazonPrimeVideo.play("video_8k.mp4")
                Toast.makeText(context, "Playing QUHD 8K video on Amazon Prime", Toast.LENGTH_SHORT)
                    .show()
            }) {
            Text("Watch QUHD 8K Video on Amazon Prime")
        }
    }
}