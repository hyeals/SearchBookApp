package com.example.searchbookapp.ui.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.main.view.input.IBookViewModelInput
import com.example.searchbookapp.ui.theme.SearchBookAppTheme

@Composable
fun BookItem(
    book: ThumbnailBook,
    input: IBookViewModelInput?
) {

    Box(
        modifier = Modifier.clickable { input?.openDetail(book.isbn13) }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            Poster(
                thumbnailBook = book
            )

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                Modifier.align(Alignment.CenterVertically)
            ) {
                Column{
                    Text(
                        text = book.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row {

                        Text(
                            text = book.subtitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = book.price,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Poster(
    thumbnailBook: ThumbnailBook
) {
    Card {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = thumbnailBook.image)
                    .apply {
                        crossfade(true)
                        scale(Scale.FILL)
                    }.build()
            ),
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
            ,
            contentScale = ContentScale.FillHeight,
            contentDescription = "Book Poster image")
    }
}

@Preview(showBackground = true)
@Composable
fun bookItemPreView() {
    SearchBookAppTheme {
        BookItem(book = ThumbnailBook(
            title = "test",
            subtitle = "subtitle",
            price = "$14.99"
        ), input = null )
    }
}