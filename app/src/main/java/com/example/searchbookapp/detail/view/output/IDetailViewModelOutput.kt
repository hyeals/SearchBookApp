package com.example.searchbookapp.detail.view.output

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface IDetailViewModelOutput {

    val detailState: StateFlow<BookDetailState>
    val detailUiEffect: SharedFlow<DetailUiEffect>
}

sealed class DetailUiEffect {
    object Back : DetailUiEffect()
}