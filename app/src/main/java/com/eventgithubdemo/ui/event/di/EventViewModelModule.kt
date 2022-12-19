package com.eventgithubdemo.ui.event.di

import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import com.eventgithubdemo.repository.ListEventRepository
import com.eventgithubdemo.ui.event.domain.GetListEventUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class EventViewModelModule {

    @Provides
    fun providerGetListEventUseCase(listEventRepository: ListEventRepository,
                                    dispatcher: CoroutinesDispatcherProvider
    ) : GetListEventUseCase  = GetListEventUseCase(listEventRepository, dispatcher)
}