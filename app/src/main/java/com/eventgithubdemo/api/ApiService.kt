package com.eventgithubdemo.api

import com.eventgithubdemo.api.model.Event
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    @GET("events")
    suspend fun getEventsApi(@QueryMap query: Map<String, String>): Response<List<Event>>

}