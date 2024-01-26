package com.example.searchbookapp.data.network.di

import com.example.searchbookapp.data.BuildConfig
import com.example.searchbookapp.data.books.BooksRemoteDataSource
import com.example.searchbookapp.data.books.BooksRemoteDataSourceImpl
import com.example.searchbookapp.data.network.api.ApiService
import com.example.searchbookapp.data.network.api.NetworkRequestFactory
import com.example.searchbookapp.data.network.retrofit.NetworkRequestFactoryImpl
import com.example.searchbookapp.data.network.retrofit.StringConverterFactory
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(interceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(logBaseUrl(baseUrl = "https://api.itbook.store/1.0/"))
            .addConverterFactory(StringConverterFactory(gson))
            .build()
    }

    private fun logBaseUrl(baseUrl: String): String {
        return baseUrl
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun bindNetworkRequestFactory(networkRequestFactory: NetworkRequestFactoryImpl): NetworkRequestFactory = networkRequestFactory
    @Provides
    @Singleton
    fun provideBooksRemoteDataSourceImpl(networkRequestFactory: NetworkRequestFactory) = BooksRemoteDataSourceImpl(networkRequestFactory)
}