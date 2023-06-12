package com.eventgithubdemo.repository

import android.util.Log
import com.eventgithubdemo.api.ApiService
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.inject.Inject

class ListEventRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ListEventRepository {

    private val MUTEX = Object()
    private var count = 0

    private val reentrantRWLock = ReentrantReadWriteLock()
    private val readLock = reentrantRWLock.readLock()
    private val writeLock = reentrantRWLock.writeLock()

    override suspend fun getEventsApi(query: QueryBuilder): Flow<ArrayList<Event>> {
//        synchronized(MUTEX) {
//        readLock.lock()
//        try {
        return flow {
            val response = apiService.getEventsApi(query.build())
            response.body()?.apply { emit((this as ArrayList<Event>)) }
        }.flowOn(dispatcherProvider.IO)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            readLock.unlock()
//        }
//        return flow {
//            emit(arrayListOf<Event>())
//        }.flowOn(dispatcherProvider.IO)
//        }
    }
}