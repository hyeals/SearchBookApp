package com.example.searchbookapp.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.model.ThumbnailBook
import com.example.searchbookapp.domain.usecase.GetThumbnailUseCase
import com.example.searchbookapp.main.view.input.IBookViewModelInput
import com.example.searchbookapp.main.view.output.BookListType
import com.example.searchbookapp.main.view.output.BookState
import com.example.searchbookapp.main.view.output.BookUiEffect
import com.example.searchbookapp.main.view.output.IBookViewModelOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getThumbnailUseCase: GetThumbnailUseCase
): ViewModel(), IBookViewModelInput, IBookViewModelOutput {

    val output: IBookViewModelOutput = this
    val input: IBookViewModelInput = this

    init {
        fetchMain()
    }

    // 책 검색을 위한 flow
    private val _searchQuery: MutableStateFlow<String> =
            MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    // 리스트 <-> 그리드 상태 처리를 위한 flow
    private val _bookListTypeState: MutableStateFlow<BookListType> =
        MutableStateFlow(BookListType.List)
    override val bookListTypeState: StateFlow<BookListType>
        get() = _bookListTypeState

    // 화면에 보여주기 위한 flow
    private val _bookState: MutableStateFlow<BookState> =
        MutableStateFlow(BookState.Loading)
    override val bookState: StateFlow<BookState>
        get() = _bookState

    // 유저로부터 입력을 받아서 Fragment 단에서 액션을 수행하기 위한 flow
    private val _bookUiEffect = MutableSharedFlow<BookUiEffect>(replay = 0)
    override val bookUiEffect: SharedFlow<BookUiEffect>
        get() = _bookUiEffect

    init {
        fetchMain()
    }

    fun fetchMain(searchInput: String = "", page: String = "0") {
        viewModelScope.launch {
            _bookState.value = BookState.Loading
            try {
                getThumbnailUseCase.getThumbnailData(
                    searchedInput = if(searchInput.isEmpty()) "new" else "search/${searchInput}",
                    page = page
                )
                    .cachedIn(viewModelScope)
                    .collect {
                        _isRefreshing.emit(false)
                        _bookState.value = BookState.Main(
                            books = MutableStateFlow(it)
                        )
                    }
            } catch (e: Exception) {
                _bookState.value = BookState.Failed(
                    e.message ?: "Unknown"
                )
            }
        }
    }
    override fun searchBooks(searchInput: String) {
        viewModelScope.launch {
            _searchQuery.value = searchInput
            _bookUiEffect.emit(
                BookUiEffect.SearchBooks(searchInput)
            )
        }
    }
    override fun openDetail(isbn13: String) {
        viewModelScope.launch {
            _bookUiEffect.emit(
                BookUiEffect.OpenBookDetail(isbn13)
            )
        }
    }

    override fun changeListType() {
        viewModelScope.launch {
            _bookListTypeState.value =
                when(bookListTypeState.value){
                    is BookListType.List -> {
                        BookListType.Grid
                    }
                    is BookListType.Grid -> {
                        BookListType.List
                    }
                }
        }
    }

    override fun refreshMain(searchInput: String) {
        viewModelScope.launch {
            fetchMain(searchInput)
        }
    }
}