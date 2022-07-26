package me.brunofelix.news.feature.data.remote

import me.brunofelix.news.BuildConfig
import me.brunofelix.news.feature.data.remote.dto.NewsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsDto>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q") searchQuery: String = "us",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Response<NewsDto>
}