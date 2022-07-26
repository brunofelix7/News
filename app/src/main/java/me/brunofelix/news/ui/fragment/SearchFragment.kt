package me.brunofelix.news.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.brunofelix.news.R
import me.brunofelix.news.adapter.NewsAdapter
import me.brunofelix.news.adapter.listener.NewsListener
import me.brunofelix.news.databinding.FragmentSearchBinding
import me.brunofelix.news.extension.snackBar
import me.brunofelix.news.extension.toast
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.presentation.UIEvent
import me.brunofelix.news.feature.presentation.news.NewsViewModel
import me.brunofelix.news.ui.NewsActivity

@AndroidEntryPoint
class SearchFragment : Fragment(), NewsListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter
    var job: Job? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        uiSetup()
        uiState()
        return binding.root
    }

    private fun uiSetup() {
        viewModel = (activity as NewsActivity).viewModel
        adapter = NewsAdapter(this)

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

    override fun onItemClick(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_nav_search_to_articleFragment,
            bundle
        )
    }
}