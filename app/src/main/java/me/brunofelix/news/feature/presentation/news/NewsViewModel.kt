package me.brunofelix.news.feature.presentation.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.brunofelix.news.core.util.Constants
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.domain.usecase.GetLocalNewsUseCase
import me.brunofelix.news.feature.domain.usecase.GetRemoteNewsUseCase
import me.brunofelix.news.feature.domain.usecase.SaveArticleUseCase
import me.brunofelix.news.feature.domain.usecase.SearchNewsUseCase
import me.brunofelix.news.feature.presentation.UIEvent
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getRemoteNewsUseCase: GetRemoteNewsUseCase,
    private val getLocalNewsUseCase: GetLocalNewsUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val searchNewsUseCase: SearchNewsUseCase
) : ViewModel() {

    private var breakingNewsPage = 1
    private val _getRemoteNews: MutableLiveData<NewsState> = MutableLiveData()
    val getRemoteNews: LiveData<NewsState> get() = _getRemoteNews

    private var searchNewsPage = 1
    private val _searchNews: MutableLiveData<NewsState> = MutableLiveData()
    val searchNews: LiveData<NewsState> get() = _searchNews

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        getBreakingNews(countryCode = "us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        getRemoteNewsUseCase.invoke(countryCode, breakingNewsPage).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _getRemoteNews.value = NewsState(isLoading = true)
                }
                is Resource.Success -> {
                    _getRemoteNews.value = NewsState(
                        isLoading = false,
                        articleList = response.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _getRemoteNews.value = NewsState(isLoading = false)
                    _uiEvent.emit(
                        UIEvent.ShowSnackBar(
                            response.message ?: Constants.ERROR_MESSAGE
                        )
                    )
                }
            }
        }.launchIn(this)
    }

    fun searchNews(query: String) = viewModelScope.launch {
        searchNewsUseCase.invoke(query, searchNewsPage).onEach { response ->
            when (response) {
                is Resource.Loading -> {
                    _searchNews.value = NewsState(isLoading = true)
                }
                is Resource.Success -> {
                    _searchNews.value = NewsState(
                        isLoading = false,
                        articleList = response.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _searchNews.value = NewsState(isLoading = false)
                    _uiEvent.emit(
                        UIEvent.ShowSnackBar(
                            response.message ?: Constants.ERROR_MESSAGE
                        )
                    )
                }
            }
        }.launchIn(this)
    }
}