package com.example.searchbookapp.common.di

import com.example.searchbookapp.data.books.BooksRemoteDataSource
import com.example.searchbookapp.data.books.BooksRemoteDataSourceImpl
import com.example.searchbookapp.data.books.BooksRepositoryImpl
import com.example.searchbookapp.domain.repository.BookRepository
import com.example.searchbookapp.domain.usecase.GetThumbnailUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FeedUseCaseModule {
    @Binds
    abstract fun bindBookRepository(booksRepositoryImpl: BooksRepositoryImpl): BookRepository

    @Binds
    abstract fun bindBooksRemoteDataSource(booksRemoteDataSourceImpl: BooksRemoteDataSourceImpl): BooksRemoteDataSource
    companion object {
        @Singleton
        @Provides
        fun provideGetThumbnailUseCase(bookRepository: BookRepository) = GetThumbnailUseCase(bookRepository)
    }

}