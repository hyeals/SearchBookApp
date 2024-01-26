package com.example.searchbookapp.domain.usecase

import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.domain.repository.BookRepository
import javax.inject.Inject

class GetThumbnailUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook> {
        return bookRepository.getThumbnailData(searchedInput)
    }
}