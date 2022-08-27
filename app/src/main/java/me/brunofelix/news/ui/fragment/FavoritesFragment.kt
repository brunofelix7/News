package me.brunofelix.news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.brunofelix.news.R
import me.brunofelix.news.adapter.NewsAdapter
import me.brunofelix.news.adapter.listener.NewsListener
import me.brunofelix.news.databinding.FragmentBreakingNewsBinding
import me.brunofelix.news.databinding.FragmentFavoritesBinding
import me.brunofelix.news.extension.snackBar
import me.brunofelix.news.extension.toast
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.presentation.UIEvent
import me.brunofelix.news.feature.presentation.news.NewsViewModel
import me.brunofelix.news.ui.NewsActivity

@AndroidEntryPoint
class FavoritesFragment : Fragment(), NewsListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding get() = _binding!!
    private lateinit var viewModel: NewsViewModel
    private lateinit var adapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        viewModel = (activity as NewsActivity).viewModel
        uiSetup()
        uiState()
        return binding.root
    }

    private fun uiSetup() {
        adapter = NewsAdapter(this)
    }

    private fun uiState() {
        viewModel.getLocalNews().observe(viewLifecycleOwner) { it ->
            adapter.differ.submitList(it.map { it.toArticle() })
            binding.newsList.adapter = adapter
        }
    }

    override fun onItemClick(article: Article) {

    }
}