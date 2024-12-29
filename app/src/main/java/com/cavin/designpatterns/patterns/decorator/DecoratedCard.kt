package com.cavin.designpatterns.patterns.decorator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DecoratedCard() {
    ShadowDecorator {
        BorderDecorator {
            PaddingDecorator {
                BaseCard {
                    Text(
                        text = "This is a decorated card!",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}