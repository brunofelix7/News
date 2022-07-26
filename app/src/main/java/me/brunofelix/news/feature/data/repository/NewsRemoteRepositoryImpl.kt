package me.brunofelix.news.feature.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.brunofelix.news.core.util.Constants
import me.brunofelix.news.core.util.Resource
import me.brunofelix.news.feature.data.remote.NewsApi
import me.brunofelix.news.feature.domain.model.Article
import me.brunofelix.news.feature.domain.repository.NewsRemoteRepository
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException
import javax.inject.Inject

class NewsRemoteRepositoryImpl @Inject constructor(
    private val api: NewsApi
) : NewsRemoteRepository {

    override suspend fun getNews(countryCode: String, pageNumber: Int): Flow<Resource<List<Article>>> = flow {
        try {
            emit(Resource.Loading())

            val response = api.getBreakingNews(countryCode, pageNumber)

            if (response.isSuccessful) {
                response.body()?.let { it ->
                    emit(Resource.Success(it.articles.map { it.toArticle() }))
                }
            } else {
                emit(Resource.Error(response.message()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: Constants.ERROR_MESSAGE))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: Constants.ERROR_MESSAGE))
        } catch (e: UnknownHostException) {
            emit(Resource.Error(e.message ?: Constants.ERROR_MESSAGE))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: Constants.ERROR_MESSAGE))
        }
    }
}