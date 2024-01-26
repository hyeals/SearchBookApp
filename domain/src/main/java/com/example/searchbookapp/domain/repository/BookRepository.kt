package com.example.searchbookapp.domain.repository

import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook

interface BookRepository {
    suspend fun getThumbnailData(searchedInput: String): EntityWrapper<BaseBook>
}