package com.eventgithubdemo.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eventgithubdemo.BuildConfig
import com.eventgithubdemo.api.request.EventsQueryBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class ApiServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: ApiService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get 5 Events`() = runBlocking {
        val queryBuilder =
            EventsQueryBuilder().setPerPage(5).setPage(1)
        val eventList = service.getEventsApi(queryBuilder.build())
        Assert.assertEquals(eventList.body()?.size, 5)
    }

    @Test
    fun `get 20 Events`() = runBlocking {
        val queryBuilder =
            EventsQueryBuilder().setPerPage(20).setPage(1)
        val eventList = service.getEventsApi(queryBuilder.build())
        Assert.assertEquals(eventList.body()?.size, 20)
    }

    @Test
    fun `get 50 Events`() = runBlocking {
        val queryBuilder =
            EventsQueryBuilder().setPerPage(50).setPage(1)
        val listEvents = service.getEventsApi(queryBuilder.build())
        Assert.assertEquals(listEvents.body()?.size, 50)
    }
}