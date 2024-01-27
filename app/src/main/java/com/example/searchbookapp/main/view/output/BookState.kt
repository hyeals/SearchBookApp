package com.example.searchbookapp.main.view.output

import androidx.paging.PagingData
import com.example.searchbookapp.domain.model.ThumbnailBook
import kotlinx.coroutines.flow.MutableStateFlow

sealed class BookState {
    object Loading: BookState()

    class Main(
        val books: MutableStateFlow<PagingData<ThumbnailBook>>
    ): BookState()
    class Failed(
        val reason: String
    ): BookState()

}