package me.brunofelix.news.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import me.brunofelix.news.R
import me.brunofelix.news.adapter.NewsAdapter
import me.brunofelix.news.databinding.ActivityArticleBinding
import me.brunofelix.news.extension.snackBar
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.presentation.news.NewsViewModel

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding
    private var article: Article? = null
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiSetup()
    }

    private fun uiSetup() {
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        article = intent.getParcelableExtra("article")

        binding.toolbar.title = article?.source?.name
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article?.url ?: "Not found")
        }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article!!)
            binding.root.snackBar("Article saved successfully")
        }
    }
}