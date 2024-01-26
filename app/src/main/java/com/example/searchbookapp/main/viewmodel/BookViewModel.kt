package com.example.searchbookapp.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.usecase.GetThumbnailUseCase
import com.example.searchbookapp.main.view.input.IBookViewModelInput
import com.example.searchbookapp.main.view.output.BookState
import com.example.searchbookapp.main.view.output.BookUiEffect
import com.example.searchbookapp.main.view.output.IBookViewModelOutput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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

    private fun fetchMain() {
        viewModelScope.launch {
            _bookState.value = BookState.Loading

            val books = getThumbnailUseCase.getThumbnailData("search/mongodb")
            _bookState.value = when(books) {
                is EntityWrapper.Success -> {
                    BookState.Main(
                        books = books.entity.books
                    )
                }
                is EntityWrapper.Fail -> {
                    BookState.Failed(
                        reason = books.error.message ?: "Unknown"
                    )
                }

            }
        }
    }

    override fun openDetail(isbn13: String) {
        viewModelScope.launch {
            _bookUiEffect.emit(
                BookUiEffect.OpenBookDetail(isbn13)
            )
        }
    }

    override fun openInfoDialog() {
        viewModelScope.launch {
            _bookUiEffect.emit(
                BookUiEffect.OpenInfoDialog
            )
        }
    }

    override fun refreshMain() {
        viewModelScope.launch {
//            _feedUiEffect.emit(
////                FeedUiEffect.OpenMovieDetail(movieName)
//            )
        }
    }
}