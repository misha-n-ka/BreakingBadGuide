package com.breakingBadGuide.di

import com.breakingBadGuide.data.BBApi
import com.breakingBadGuide.repositories.MainRepository
import com.breakingBadGuide.repositories.Repository
import com.breakingBadGuide.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBBApi(): BBApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BBApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(api: BBApi): Repository = MainRepository(api)
}