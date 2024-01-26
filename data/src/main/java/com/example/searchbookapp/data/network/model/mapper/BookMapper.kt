package com.example.searchbookapp.data.network.model.mapper

import com.example.searchbookapp.data.network.model.converter.toThumbnailBook
import com.example.searchbookapp.data.network.model.response.BookResponse
import com.example.searchbookapp.domain.model.BaseBook
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import javax.inject.Inject

class BookMapper @Inject constructor()
    : BaseMapper<BookResponse, BaseBook>() {
    override fun getSuccess(
        model: BookResponse?,
        extra: Any?
    ): EntityWrapper.Success<BaseBook> {
        val thumbnailBook = mutableListOf<ThumbnailBook>()
        return model?.let { it ->
            EntityWrapper.Success(
                entity = BaseBook(
                    total = model.total,
                    page = model.page ?: "",
                    books = thumbnailBook.apply {
                        addAll(model.books.map { it.toThumbnailBook() })
                    }
                )
            )
        } ?: EntityWrapper.Success(
            entity = BaseBook()
        )
    }

    override fun getFailure(error: Throwable): EntityWrapper.Fail<BaseBook> {
        return EntityWrapper.Fail(error)
    }
}