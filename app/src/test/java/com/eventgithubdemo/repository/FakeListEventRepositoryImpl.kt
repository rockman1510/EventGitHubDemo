package com.eventgithubdemo.repository

import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class FakeListEventRepositoryImpl(
    private val fakeData: ArrayList<Event>,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ListEventRepository {
    override suspend fun getEventsApi(query: QueryBuilder): Flow<ArrayList<Event>> {
        return flow {
            emit(fakeData)
        }.flowOn(dispatcherProvider.IO)
    }
}