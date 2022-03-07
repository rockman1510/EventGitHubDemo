package com.eventgithubdemo.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eventgithubdemo.BuildConfig
import com.eventgithubdemo.MainCoroutineRule
import com.eventgithubdemo.api.ApiService
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.EventsQueryBuilder
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ListEventRepositoryImplTest {

    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var listEventRepositoryImpl: ListEventRepositoryImpl

    @Before
    fun setUp() {
        val dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
        mockWebServer = MockWebServer()
        val service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BuildConfig.BASE_URL))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
        listEventRepositoryImpl = ListEventRepositoryImpl(service, dispatcherProvider)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getEventsApi - 5`() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(5).setPage(1)
        listEventRepositoryImpl.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 5)
    }

    @Test
    fun `getEventsApi - 20`() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(20).setPage(2)
        listEventRepositoryImpl.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 20)
    }

    @Test
    fun `getEventsApi - 50`() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(50).setPage(2)
        listEventRepositoryImpl.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 50)
    }

    @Test
    fun `getEventsApi - 100`() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(100).setPage(1)
        listEventRepositoryImpl.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        Assert.assertNotNull(dataList)
        Assert.assertEquals(dataList.size, 100)
    }
}