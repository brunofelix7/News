package me.brunofelix.news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brunofelix.news.adapter.NewsAdapter
import me.brunofelix.news.adapter.listener.NewsListener
import me.brunofelix.news.databinding.FragmentBreakingNewsBinding
import me.brunofelix.news.extension.navigateToArticleActivity
import me.brunofelix.news.extension.snackBar
import me.brunofelix.news.extension.toast
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.presentation.UIEvent
import me.brunofelix.news.feature.presentation.news.NewsViewModel
import me.brunofelix.news.ui.NewsActivity

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(), NewsListener {

    private var _binding: FragmentBreakingNewsBinding? = null
    private val binding: FragmentBreakingNewsBinding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
        uiSetup()
        uiState()
        return binding.root
    }

    private fun uiSetup() {
        viewModel = (activity as NewsActivity).viewModel
        adapter = NewsAdapter(this)
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

        viewModel.getRemoteNews.observe(viewLifecycleOwner) { state ->
            binding.progressBar.isVisible = state.isLoading

            if (state.articleList.isEmpty() && !state.isLoading) {
                binding.textNoContent.isVisible = true
                binding.newsList.isVisible = false
            } else {
                binding.textNoContent.isVisible = false
                binding.newsList.isVisible = true

                state.articleList.let {
                    adapter.differ.submitList(it)
                    binding.newsList.adapter = adapter
                }
            }
        }
    }

    override fun onItemClick(article: Article) {
        activity?.navigateToArticleActivity(requireContext(), article)
    }
}