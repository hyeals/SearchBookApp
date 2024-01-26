package com.example.searchbookapp.main.view.output

sealed class BookListType {
    object List: BookListType()
    object Grid: BookListType()
}