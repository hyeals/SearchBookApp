package com.example.searchbookapp.main.view.input

interface IBookViewModelInput {
    fun openDetail(isbn13: String)
    fun openInfoDialog()
    fun refreshMain()
}