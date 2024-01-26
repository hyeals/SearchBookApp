package com.example.searchbookapp.detail.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.searchbookapp.main.view.screen.MainScreen
import com.example.searchbookapp.main.viewmodel.BookViewModel
import com.example.searchbookapp.ui.theme.SearchBookAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SearchBookAppTheme() {
                }
            }
        }
    }
}