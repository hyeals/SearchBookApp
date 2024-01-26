package com.example.searchbookapp.domain.usecase

import com.example.searchbookapp.domain.model.BookDetail
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.repository.BookRepository
import javax.inject.Inject

class GetDetailBookUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend fun getDetailBookData(isbn13: String): EntityWrapper<BookDetail> {
        return bookRepository.getDetailBookData(isbn13)
    }
}