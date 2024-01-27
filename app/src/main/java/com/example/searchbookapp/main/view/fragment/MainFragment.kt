package com.example.searchbookapp.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.searchbookapp.main.view.output.BookUiEffect
import com.example.searchbookapp.main.view.screen.MainScreen
import com.example.searchbookapp.main.viewmodel.BookViewModel
import com.example.searchbookapp.ui.navigation.safeNavigate
import com.example.searchbookapp.ui.theme.SearchBookAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment: Fragment() {

    private val viewModel: BookViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observeUiEffects()

        return ComposeView(requireContext()).apply {
            setContent {
                SearchBookAppTheme() {
                    MainScreen(
                        searchTextStateHolder = viewModel.searchQuery.collectAsState(),
                        bookStateHolder = viewModel.output.bookState.collectAsState(),
                        bookListTypeStateHolder = viewModel.output.bookListTypeState.collectAsState(),
                        input = viewModel.input
                    )
                }
            }
        }
    }

    private fun observeUiEffects() {
        val navController = findNavController()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.output.bookUiEffect.collectLatest {
                    when (it) {
                        is BookUiEffect.OpenBookDetail -> {
                            navController.safeNavigate(
                                MainFragmentDirections.actionMainToDetail(it.isbn13)
                            )
                        }
                        is BookUiEffect.SearchBooks -> {
                            viewModel.fetchMain(it.searchInput)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreView() {
    SearchBookAppTheme {

    }
}