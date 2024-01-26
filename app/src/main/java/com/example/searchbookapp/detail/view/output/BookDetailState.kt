package com.example.searchbookapp.detail.view.output

import com.example.searchbookapp.domain.model.BookDetail

sealed class BookDetailState {
    object Initial : BookDetailState()

    object Loading: BookDetailState()
    class Main(val bookDetail: BookDetail) : BookDetailState()

    class Failed(val reason: String): BookDetailState()
}