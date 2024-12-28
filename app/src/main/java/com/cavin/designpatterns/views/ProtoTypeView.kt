package com.cavin.designpatterns.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cavin.designpatterns.patterns.prototype.AppDocument

@Composable
fun ProtoTypeView() {
    // Original prototype
    val originalDocument = AppDocument(
        title = "Prototype Pattern",
        content = "This is the original document content.",
        author = "John Doe"
    )

    // Clone the prototype and modify properties
    val clonedDocument = originalDocument.copy(
        title = "Cloned Prototype",
        content = "This is the cloned document content."
    )

    // UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Documents", style = MaterialTheme.typography.titleLarge)

        // Display Original Document
        DocumentCard(document = originalDocument, modifier = Modifier.fillMaxWidth())

        // Display Cloned Document
        DocumentCard(document = clonedDocument, modifier = Modifier.fillMaxWidth())
    }
}
@Composable
fun DocumentCard(document: AppDocument, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.padding(8.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${document.title}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Content: ${document.content}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Author: ${document.author}", style = MaterialTheme.typography.bodySmall)
        }
    }
}