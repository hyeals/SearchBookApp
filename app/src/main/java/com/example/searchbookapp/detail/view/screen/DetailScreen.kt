package com.example.searchbookapp.detail.view.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.searchbookapp.R
import com.example.searchbookapp.detail.view.input.IDetailViewModelInput
import com.example.searchbookapp.detail.view.output.BookDetailState
import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.ui.book.BookMeta
import com.example.searchbookapp.ui.theme.Paddings
import com.example.searchbookapp.ui.theme.SearchBookAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    refreshStateHolder: State<Boolean>,
    bookDetailState: State<BookDetailState>,
    input: IDetailViewModelInput?
) {
    val bookDetail by bookDetailState
    val pullRefreshState = rememberPullRefreshState(refreshing = refreshStateHolder.value, onRefresh = { input!!.refreshDetailBook() })

    when(bookDetail) {
        is BookDetailState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState)
                    .verticalScroll(rememberScrollState())
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
                PullRefreshIndicator(refreshing = refreshStateHolder.value, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
            }
        }
        is BookDetailState.Main -> {
            BookDetail(
                isRefresh = refreshStateHolder.value,
                book = (bookDetail as BookDetailState.Main).bookDetail,
                input = input
            )
        }

        is BookDetailState.Failed -> {}

        else -> {}
    }

    BackHandler {
        input?.goBackToMain()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetail(
    isRefresh: Boolean,
    book: BookDetail,
    input: IDetailViewModelInput?
) {
    val scrollState = rememberScrollState()
    val pullRefreshState = rememberPullRefreshState(refreshing = isRefresh, onRefresh = { input!!.refreshDetailBook() })

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                modifier = Modifier.requiredHeight(70.dp),
                navigationIcon = {
                    IconButton(onClick = { input?.goBackToMain() }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Paddings.extra)
            ) {
                // Poster
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    BigPoster(
                        book = book
                    )
                }

                Spacer(modifier = Modifier.height(Paddings.xlarge))

                // Book Detail Data
                BookMeta(key = "Title", value = book.title)
                BookMeta(key = "SubTitle", value = book.subtitle)
                BookMeta(key = "Authors", value = book.authors)
                BookMeta(key = "Publisher", value = book.publisher)
                BookMeta(key = "Isbn10", value = book.isbn10)
                BookMeta(key = "Isbn13", value = book.isbn13)
                BookMeta(key = "pages", value = book.pages)
                BookMeta(key = "year", value = book.year)
                BookMeta(key = "rating", value = book.rating)
                BookMeta(key = "price", value = book.price)
                BookMeta(key = "desc", value = book.desc)
                BookMeta(key = "url", value = book.url)

                if(!book.pdf.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(Paddings.xlarge))
                    androidx.compose.material.Text(
                        modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp),
                        text = "pdf",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )

                    for ((pdfKey, pdfData) in book.pdf!!) {
                        BookMeta(key = pdfKey, value = pdfData)
                    }
                }
            }
            PullRefreshIndicator(refreshing = isRefresh, state = pullRefreshState, modifier = Modifier.align(Alignment.TopCenter))
        }
    }
}


@Composable
fun BigPoster(
    book: BookDetail
) {
    Card {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = book.image)
                    .apply {
                        crossfade(true)
                        scale(Scale.FILL)
                    }.build()
            ),
            modifier = Modifier
                .width(180.dp)
                .height(250.dp),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Book Poster Image"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun detailScreenPreView() {
    SearchBookAppTheme {
        BookDetail(
            isRefresh = false,
            BookDetail(
                title = "title",
                subtitle = "subtitle",
                authors = "authors",
                publisher = "publisher",
                isbn10 = "isbn10",
                isbn13 = "isbn13",
                pages = "pages",
                year = "year",
                rating = "rating",
                price = "price",
                desc = "desc",
                url = "url"
            ),
            null
        )
    }
}