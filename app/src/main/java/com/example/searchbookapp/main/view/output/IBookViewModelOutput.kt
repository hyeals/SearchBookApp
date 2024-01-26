package com.example.searchbookapp.main.view.output

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IBookViewModelOutput {
    val bookState: StateFlow<BookState>
    val bookUiEffect: SharedFlow<BookUiEffect>
}

sealed class BookUiEffect {
    data class OpenBookDetail(val isbn13: String): BookUiEffect()
    data class SearchBooks(val searchInput: String): BookUiEffect()
}