package com.eventgithubdemo.ui.domain

import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import com.eventgithubdemo.repository.ListEventRepository
import com.eventgithubdemo.ui.event.domain.GetListEventUseCase
import kotlinx.coroutines.flow.Flow

class FakeGetListEventUseCase(private val listEventRepository: ListEventRepository,
                              private val dispatcherProvider: CoroutinesDispatcherProvider
): GetListEventUseCase(listEventRepository, dispatcherProvider) {

    override suspend fun getEventsData(query: QueryBuilder): Flow<ArrayList<Event>> {
        return listEventRepository.getEventsApi(query)
    }

}