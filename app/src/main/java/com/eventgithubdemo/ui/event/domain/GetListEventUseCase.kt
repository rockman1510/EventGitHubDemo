package com.eventgithubdemo.ui.event.domain

import android.util.Log
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.QueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import com.eventgithubdemo.repository.ListEventRepository
import com.eventgithubdemo.repository.ListEventRepositoryImpl
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class GetListEventUseCase @Inject constructor(
    private val listEventRepository: ListEventRepository,
    private val dispatcher: CoroutinesDispatcherProvider
    ) {

    private val superVisorJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(superVisorJob)

    open suspend fun getEventsData(query: QueryBuilder): Flow<ArrayList<Event>> {
        return listEventRepository.getEventsApi(query)
    }

    // second method to load data
    suspend fun loadingEventsData(query: QueryBuilder, onError: (message: String) -> Unit): ArrayList<Event> = withContext(dispatcher.IO){
        Log.d("HuyLV", "loadingEventsData")
        coroutineScope.async {
            var eventList = arrayListOf<Event>()
            try{
                withTimeout(2000L){
                    listEventRepository.getEventsApi(query).collect{
                        eventList = it
                    }
                }
            } catch (e: Exception){
                e.printStackTrace()
                onError.invoke(e.message.toString())
            }
            Log.d("HuyLV", "loadingEventsData - eventList: $eventList")
            return@async eventList
        }.await()
    }

    suspend fun loading() : String = withContext(dispatcher.IO){
        Log.d("HuyLV", "loading")
        var string1 = "1"
        var string2 = "2"
        coroutineScope.async {
            try{
                withTimeout(2000L){
                    listOf(
                        launch{
                            delay(1700L)
                            string1 = "String 1"
                            Log.d("HuyLV", "Job1 finished")
                        },
                        launch{
                            delay(1700L)
                            string2 = "String 2"
                            Log.d("HuyLV", "Job2 finished")
                        }
                    ).joinAll()
                }
            }catch(e: Exception){
                e.printStackTrace()
            }
            return@async "$string1 and $string2"
        }.await()
    }

//    // third method to load data
//    suspend operator fun invoke(query: QueryBuilder, onError: (message: String) -> Unit) : ArrayList<Event>{
//        Log.d("HuyLV", "invoke")
//        return loadingEventsData(query, onError)
//    }

    fun onCancel(){
        superVisorJob.cancel()
    }

}