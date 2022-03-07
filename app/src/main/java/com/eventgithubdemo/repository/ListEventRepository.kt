package com.eventgithubdemo.repository

import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import kotlinx.coroutines.flow.Flow

interface ListEventRepository {
    suspend fun getEventsApi(query: QueryBuilder): Flow<ArrayList<Event>>
}