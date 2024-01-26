package com.example.searchbookapp.main.view.output

import com.example.searchbookapp.domain.model.ThumbnailBook

sealed class BookState {
    object Loading: BookState()
    class Main(
        val books: List<ThumbnailBook>
    ): BookState()
    class Failed(
        val reason: String
    ): BookState()

}