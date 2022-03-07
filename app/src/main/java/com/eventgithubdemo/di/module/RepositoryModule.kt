package com.eventgithubdemo.di.module

import com.eventgithubdemo.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideEventsRepository(eventRepositoryImpl: ListEventRepositoryImpl): ListEventRepository
}