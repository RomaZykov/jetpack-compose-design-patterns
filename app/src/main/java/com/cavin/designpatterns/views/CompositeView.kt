package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.composite.Category
import com.cavin.designpatterns.patterns.composite.Product

@Composable
fun CompositeView() {
    val categories = remember {
        listOf(
            Category(
                name = "Desktop Computer",
                children = listOf(
                    Product(
                        "Main Board",
                        "Part of the computer",
                        "https://via.placeholder.com/150",
                        1000.0
                    ),
                    Product(
                        "CPU",
                        "Part of the computer",
                        "https://via.placeholder.com/150",
                        2000.0
                    )
                )
            ),
            Category(
                name = "Car",
                children = listOf(
                    Product(
                        "Electric Car",
                        "Type of the car",
                        "https://via.placeholder.com/150",
                        9000.0
                    ),
                    Product(
                        "Wheel",
                        "Part of the car",
                        "https://via.placeholder.com/150",
                        1000.0
                    )
                )
            )
        )

    }

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            items(categories) { category ->
                category.Render()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShoppingCart() {
    MaterialTheme {
        CompositeView()
    }
}