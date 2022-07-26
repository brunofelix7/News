package me.brunofelix.news.feature.data.repository

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

    override suspend fun getNews(): Resource<List<Article>> {
        try {
            val response = api.getBreakingNews()
            if (response.isSuccessful && response.body() != null) {
                return Resource.Success(response.body()?.articles?.map { it.toArticle() })
            }
            return Resource.Error(Constants.ERROR_MESSAGE)
        } catch (e: IOException) {
            return Resource.Error(e.message ?: Constants.ERROR_MESSAGE)
        } catch (e: HttpException) {
            return Resource.Error(e.message ?: Constants.ERROR_MESSAGE)
        } catch (e: UnknownHostException) {
            return Resource.Error(e.message ?: Constants.ERROR_MESSAGE)
        } catch (e: Exception) {
            return Resource.Error(e.message ?: Constants.ERROR_MESSAGE)
        }
    }
}