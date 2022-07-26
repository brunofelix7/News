package me.brunofelix.news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.brunofelix.news.R
import me.brunofelix.news.adapter.NewsAdapter
import me.brunofelix.news.databinding.FragmentFavoritesBinding
import me.brunofelix.news.databinding.FragmentSearchBinding
import me.brunofelix.news.extension.snackBar
import me.brunofelix.news.extension.toast
import me.brunofelix.news.feature.presentation.UIEvent
import me.brunofelix.news.feature.presentation.news.NewsViewModel
import me.brunofelix.news.ui.NewsActivity

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    var job: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = (activity as NewsActivity).viewModel
        uiSetup()
        uiState()
        return binding.root
    }

    private fun uiSetup() {
        binding.inputQuery.addTextChangedListener {
            job?.cancel()
            job = lifecycleScope.launch {
                delay(500L)
                it?.let {
                    if (it.toString().trim().isNotEmpty()) {
                        viewModel.searchNews(it.toString())
                    }
                }
            }
        }
    }

    private fun uiState() {
        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UIEvent.ShowToast -> {
                        activity?.toast(event.message)
                    }
                    is UIEvent.ShowSnackBar -> {
                        binding.root.snackBar(event.message)
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading

            if (state.articleList.isEmpty() && !state.isLoading) {
                binding.newsList.isVisible = false
            } else {
                binding.newsList.isVisible = true

                state.articleList.let {
                    val adapter = NewsAdapter()
                    adapter.differ.submitList(it)
                    binding.newsList.adapter = adapter
                }
            }
        }
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }
}