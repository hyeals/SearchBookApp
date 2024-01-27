package com.example.searchbookapp.domain.usecase

import androidx.paging.PagingData
import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThumbnailUseCase @Inject constructor(
    private val bookRepository: BookRepository
){
    suspend fun getThumbnailData(searchedInput: String, page: String): Flow<PagingData<ThumbnailBook>> {
        return bookRepository.getThumbnailData(searchedInput, page)
    }
}