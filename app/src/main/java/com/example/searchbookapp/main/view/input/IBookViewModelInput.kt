package com.example.searchbookapp.main.view.input

interface IBookViewModelInput {

    fun searchBooks(searchInput: String)
    fun openDetail(isbn13: String)
    fun changeListType()
    fun refreshMain()
}