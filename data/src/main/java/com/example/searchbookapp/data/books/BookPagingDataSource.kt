package com.example.searchbookapp.data.books

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception


class BookPagingDataSource(
    private val remoteDataSource: BooksRemoteDataSource,
    private val searchInput: String
) : PagingSource<Int, ThumbnailBook>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ThumbnailBook> {
        return try {
            val currentPage = if (searchInput == "new") -1 else params.key ?: 1
            val books = remoteDataSource.getThumbnailData(
                searchedInput = searchInput,
                page = currentPage.toString()
            )
            var bookList: List<ThumbnailBook> = emptyList()
            var bookPage: String = "0"
            var bookTotal: String = "0"

            if(books is EntityWrapper.Success) {
                bookList = books.entity.books
                bookPage = books.entity.page
                bookTotal = books.entity.total
            } else if(books is EntityWrapper.Fail){
                throw Exception(books.error)
            }

            // 초기 상태 이거나 검색 결과가 없는 경우
            if(currentPage == -1 || bookTotal == "0") {
                LoadResult.Page(
                    data = bookList,
                    prevKey = null,
                    nextKey = null
                )
            } else {
                LoadResult.Page(
                    data = bookList,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (bookList.isEmpty()) null else bookPage.toInt() + 1
                )
            }
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ThumbnailBook>): Int? {
        return state.anchorPosition
    }

}