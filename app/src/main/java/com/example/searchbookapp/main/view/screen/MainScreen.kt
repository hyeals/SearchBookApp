package com.example.searchbookapp.main.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.searchbookapp.R
import com.example.searchbookapp.main.view.input.IBookViewModelInput
import com.example.searchbookapp.main.view.output.BookListType
import com.example.searchbookapp.main.view.output.BookState
import com.example.searchbookapp.ui.book.BookGridItem
import com.example.searchbookapp.ui.book.BookListItem
import com.example.searchbookapp.ui.theme.Paddings

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    searchTextStateHolder: State<String>,
    bookStateHolder: State<BookState>,
    bookListTypeStateHolder: State<BookListType>,
    input: IBookViewModelInput
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchTextStateHolder.value,
                onValueChange = {
                    input.searchBooks(it)
                },
                singleLine = true,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_search_24),
                        contentDescription = ""
                    )
                }
            )
            
            Spacer(modifier = Modifier.width(24.dp))

            // list <-> grid
            IconButton(
                onClick = {
                    input.changeListType()
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_format_list_bulleted_24),
                    contentDescription = null
                )
            }
        }

        BodyContent(
            bookListType = bookListTypeStateHolder.value,
            bookState = bookStateHolder.value,
            input = input
        )
    }
}

@Composable
fun BodyContent(
    bookListType: BookListType,
    bookState: BookState,
    input: IBookViewModelInput
) {
    when (bookState) {
        is BookState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        is BookState.Main -> {
            when(bookListType) {
                is BookListType.List -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        itemsIndexed(bookState.books) { _, book ->
                            BookListItem(
                                book = book,
                                input = input
                            )
                        }
                    }
                }

                is BookListType.Grid -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3)
                    ) {
                        itemsIndexed(bookState.books) { _, book ->
                            BookGridItem(
                                book = book,
                                input = input
                            )
                        }
                    }
                }
            }
        }

        is BookState.Failed -> {
            RetryMessage(
                message = bookState.reason,
                input = input
            )
        }
    }
}

val IMAGE_SIZE = 48.dp

@Composable
fun RetryMessage(
    modifier: Modifier = Modifier,
    message: String,
    input: IBookViewModelInput
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .requiredSize(IMAGE_SIZE),
            // todo 이미지 변경 필요
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_launcher_background),
            contentDescription = null
        )

        Text(
            text = message,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                top = Paddings.xlarge,
                bottom = Paddings.extra
            )
        )

        Button(
            onClick = { input.refreshMain() }
        ) {
            Text("RETRY")
        }
    }
}