package me.brunofelix.news.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import me.brunofelix.news.R
import me.brunofelix.news.databinding.FragmentArticleBinding
import me.brunofelix.news.databinding.FragmentSearchBinding
import me.brunofelix.news.feature.presentation.news.NewsViewModel
import me.brunofelix.news.ui.NewsActivity

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding: FragmentArticleBinding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    private val args: ArticleFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        uiSetup()
        return binding.root
    }

    private fun uiSetup() {
        viewModel = (activity as NewsActivity).viewModel

        val article = args.article

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url ?: "Not found")
        }
    }
}