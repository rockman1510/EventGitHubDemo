package com.eventgithubdemo.ui.event

import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.EventsQueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import com.eventgithubdemo.base.mvi.BaseViewModel
import com.eventgithubdemo.constant.Constants
import com.eventgithubdemo.ui.event.domain.GetListEventUseCase
import com.eventgithubdemo.ui.event.mvi.EventsIntent
import com.eventgithubdemo.ui.event.mvi.EventsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListEventViewModel @Inject constructor(
    private val getListEventUseCase: GetListEventUseCase,
    private val dispatcher: CoroutinesDispatcherProvider,
    private val handler: Handler
) : BaseViewModel<EventsIntent, EventsState>() {

    override val state: MutableLiveData<EventsState> = MutableLiveData()
    private val eventList = arrayListOf<Event>()
    private val runnableTask = Runnable { processIntent(EventsIntent.FetchData(true)) }

    init {
        processIntent(EventsIntent.FetchData(true))
    }

    override fun processIntent(intent: EventsIntent) {
        state.postValue(EventsState.OnLoading)
        when (intent) {
            is EventsIntent.FetchData -> {
                loadData(intent)
            }
            is EventsIntent.SaveSuccessState -> {
                state.postValue(EventsState.OnSuccess(eventList))
            }
        }
    }

    private fun loadData(intent: EventsIntent.FetchData) {
        viewModelScope.launch(dispatcher.Main) {
            try {
                var newData = arrayListOf<Event>()
                val eventsQueryBuilder = EventsQueryBuilder().setPerPage(intent.perPage)
                getListEventUseCase.getEventsData(eventsQueryBuilder).collect {
                    newData = it
                    if (!eventList.containsAll(newData)) {
                        eventList.addAll(0, newData)
                    }
                }
//                var message = ""
//                listOf(launch { newData = getListEventUseCase.loadingEventsData(eventsQueryBuilder, ::onError) },
//                launch { message = getListEventUseCase.loading() }).joinAll()
//                Log.d("HuyLV", "loadData: $message")
//                val newData = getListEventUseCase.invoke(eventsQueryBuilder, ::onError)
                if(!eventList.containsAll(newData)){
                    eventList.addAll(0, newData)
                }
                if (newData.isNullOrEmpty()) {
                    onError("No Data!")
                } else {
                    state.postValue(EventsState.OnSuccess(eventList))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                onError(e.message.toString())
            } finally {
                if (intent.isPeriodic) {
                    handler.postDelayed(runnableTask, Constants.PERIODIC_TIME)
                }
            }
        }
    }

    private fun onError(errorMessage: String) {
        state.postValue(EventsState.OnError(errorMessage))
    }

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacks(runnableTask)
        getListEventUseCase.onCancel()
    }
}