package me.brunofelix.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.brunofelix.news.adapter.listener.NewsListener
import me.brunofelix.news.core.util.loadImage
import me.brunofelix.news.databinding.ItemNewsBinding
import me.brunofelix.news.feature.domain.model.Article
import timber.log.Timber

class NewsAdapter constructor(
    private val listener: NewsListener
) : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val root = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(root)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.setIsRecyclable(false)
        holder.bind(article)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ArticleViewHolder(
        private val binding: ItemNewsBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            if (article.urlToImage != null) {
                loadImage(binding.imgUrl, article.urlToImage)
            }
            binding.textTitle.text = article.title
            binding.textDescription.text = article.description
            binding.textSource.text = article.source?.name
            binding.textPublishedAt.text = article.publishedAt

            binding.content.setOnClickListener {
                listener.onItemClick(article)
            }
        }
    }
}
