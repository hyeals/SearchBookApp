package com.example.searchbookapp.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchbookapp.detail.view.input.IDetailViewModelInput
import com.example.searchbookapp.detail.view.output.BookDetailState
import com.example.searchbookapp.detail.view.output.DetailUiEffect
import com.example.searchbookapp.detail.view.output.IDetailViewModelOutput
import com.example.searchbookapp.domain.model.EntityWrapper
import com.example.searchbookapp.domain.usecase.GetDetailBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailBookUseCase: GetDetailBookUseCase
) : ViewModel(), IDetailViewModelInput, IDetailViewModelOutput {

    val inputs: IDetailViewModelInput = this
    val outputs: IDetailViewModelOutput = this

    private val _detailState: MutableStateFlow<BookDetailState> =
        MutableStateFlow(BookDetailState.Initial)
    override val detailState: StateFlow<BookDetailState>
        get() = _detailState

    private val _detailUiEffect = MutableSharedFlow<DetailUiEffect>(replay = 0)
    override val detailUiEffect: SharedFlow<DetailUiEffect>
        get() = _detailUiEffect

    suspend fun initDetailBook(isbn: String) {
        viewModelScope.launch {
            _detailState.value = BookDetailState.Loading

            val bookDetail = getDetailBookUseCase.getDetailBookData(isbn13 = isbn)
            _detailState.value = when(bookDetail) {
                is EntityWrapper.Success -> {
                    BookDetailState.Main(
                        bookDetail = bookDetail.entity
                    )
                }

                is EntityWrapper.Fail -> {
                    BookDetailState.Failed(
                        reason = bookDetail.error.message ?: "Unknown"
                    )
                }
            }

        }
    }

    override fun goBackToMain() {
        viewModelScope.launch {
            _detailUiEffect.emit(
                DetailUiEffect.Back
            )
        }
    }
}