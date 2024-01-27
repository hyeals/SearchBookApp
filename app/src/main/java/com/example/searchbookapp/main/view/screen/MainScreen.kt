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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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
    refreshStateHolder: State<Boolean>,
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
            isRefreshing = refreshStateHolder.value,
            searchInput = searchTextStateHolder.value,
            bookListType = bookListTypeStateHolder.value,
            bookState = bookStateHolder.value,
            input = input
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BodyContent(
    isRefreshing: Boolean,
    searchInput: String,
    bookListType: BookListType,
    bookState: BookState,
    input: IBookViewModelInput
) {

    val pullRefreshState = rememberPullRefreshState(isRefreshing, { input.refreshMain(searchInput) })

    when(bookState) {
        is BookState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
            }
        }
        is BookState.Main -> {
            val bookItemState = bookState.books.collectAsLazyPagingItems()

            val isLoading = bookItemState.loadState.refresh is LoadState.Loading
            val isError = bookItemState.loadState.refresh is LoadState.Error

            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                            .verticalScroll(rememberScrollState())
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                        PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                    }
                }
                isError -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                            .verticalScroll(rememberScrollState())
                    ) {
                        RetryMessage(
                            searchInput = searchInput,
                            message = stringResource(id = R.string.retry),
                            input = input
                        )
                        PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                    }
                }
                else -> {
                    when(bookListType) {
                        is BookListType.List -> {
                            Box(
                                Modifier.pullRefresh(pullRefreshState)
                            ) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(bookItemState.itemCount) {index ->
                                        BookListItem(
                                            book = bookItemState[index]!!,
                                            input = input
                                        )
                                    }
                                }
                                PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                            }
                        }

                        is BookListType.Grid -> {
                            Box(
                                Modifier.pullRefresh(pullRefreshState)
                            ) {
                                LazyVerticalGrid(
                                    columns = GridCells.Fixed(3)
                                ) {
                                    items(bookItemState.itemCount) { index ->
                                        BookGridItem(
                                            book = bookItemState[index]!!,
                                            input = input
                                        )
                                    }
                                }
                                PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
                            }
                        }
                    }
                }
            }

        }
        is BookState.Failed -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
            ) {
                RetryMessage(
                    searchInput = searchInput,
                    message = bookState.reason,
                    input = input
                )
                PullRefreshIndicator(refreshing = isRefreshing, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
            }
        }
    }
}

val IMAGE_SIZE = 48.dp

@Composable
fun RetryMessage(
    searchInput: String,
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
            imageVector = ImageVector.vectorResource(id = R.drawable.baseline_warning_24),
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
            onClick = { input.refreshMain(searchInput) }
        ) {
            Text("RETRY")
        }
    }
}