package com.example.searchbookapp.ui.book

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.searchbookapp.ui.theme.Paddings
import com.example.searchbookapp.ui.theme.SearchBookAppTheme

@Composable
fun BookMeta(
    modifier: Modifier = Modifier,
    key: String,
    value: String
) {
    Row(modifier = modifier
        .padding(4.dp)) {
        // Key
        Text(
            modifier = Modifier.width(100.dp),
            text = key,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.width(Paddings.large))

        // Value
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = value,
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookMetaPreView() {
    SearchBookAppTheme {
        BookMeta(key = "publisher", value = "publisher")
    }
}