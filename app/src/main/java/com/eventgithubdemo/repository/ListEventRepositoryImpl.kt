package com.eventgithubdemo.repository

import com.eventgithubdemo.api.ApiService
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ListEventRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ListEventRepository {

    override suspend fun getEventsApi(query: QueryBuilder): Flow<ArrayList<Event>> {
        return flow {
            val response = apiService.getEventsApi(query.build())
            response.body()?.apply { emit((this as ArrayList<Event>)) }
        }.flowOn(dispatcherProvider.IO)
    }
}