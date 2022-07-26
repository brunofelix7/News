package me.brunofelix.news.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.brunofelix.news.BuildConfig
import me.brunofelix.news.feature.data.local.NewsDatabase
import me.brunofelix.news.feature.data.local.converter.Converters
import me.brunofelix.news.feature.data.remote.NewsApi
import me.brunofelix.news.feature.data.repository.NewsLocalRepositoryImpl
import me.brunofelix.news.feature.data.repository.NewsRemoteRepositoryImpl
import me.brunofelix.news.feature.domain.repository.NewsLocalRepository
import me.brunofelix.news.feature.domain.repository.NewsRemoteRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(): NewsApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideClient())
        .build()
        .create(NewsApi::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news_db"
        ).addTypeConverter(Converters()).build()
    }

    @Provides
    @Singleton
    fun provideNewsLocalRepository(db: NewsDatabase): NewsLocalRepository {
        return NewsLocalRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideNewsRemoteRepository(api: NewsApi): NewsRemoteRepository {
        return NewsRemoteRepositoryImpl(api)
    }
}