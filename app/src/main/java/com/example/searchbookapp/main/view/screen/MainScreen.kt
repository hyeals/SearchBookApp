package com.example.searchbookapp.main.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.searchbookapp.R
import com.example.searchbookapp.main.view.input.IBookViewModelInput
import com.example.searchbookapp.main.view.output.BookState
import com.example.searchbookapp.ui.book.BookItem
import com.example.searchbookapp.ui.theme.Paddings

val COMMON_HORIZONTAL_PADDING = Paddings.medium
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    bookStateHolder: State<BookState>,
    input: IBookViewModelInput
) {

    Scaffold( // Scaffold: topBar와 body가 나뉘어져 있는 정규화된(표준화된) 형태의 화면을 구조하기 위한 컴포넌트
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                modifier = Modifier
                    .background(MaterialTheme.colors.primarySurface)
                    .requiredHeight(70.dp),
                title = {
                    Text(
                        modifier = Modifier.padding(
                            start = COMMON_HORIZONTAL_PADDING
                        ),
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.h3
                    )
                },
                actions = {
//                    AppBarMenu(
//                        btnColor = btnColor,
//                        changeAppColor = changeAppColor,
//                        input = input
//                    )
                }
            )
        }
    ) {
        BodyContent(
            bookState = bookStateHolder.value,
            input = input
        )
    }
}

@Composable
fun AppBarMenu(
    btnColor: Color,
    changeAppColor: () -> Unit,
    input: IBookViewModelInput
) {
    Row(
        modifier = Modifier.padding(
            end = COMMON_HORIZONTAL_PADDING
        )
    ) {
        IconButton(
            onClick = {
                changeAppColor()
            }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color = btnColor)
            )
        }

        IconButton(
            onClick = {
                input.openInfoDialog()
            }
        ) {
            Icon(
                // todo 이미지 변경 필요
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "Information",
                tint = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun BodyContent(
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(bookState.books) { _, book ->
                    BookItem(
                        book = book,
                        input = input
                    )
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