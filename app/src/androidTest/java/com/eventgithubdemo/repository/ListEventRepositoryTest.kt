package com.eventgithubdemo.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.api.request.EventsQueryBuilder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ListEventRepositoryTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var listEventRepository: ListEventRepository


    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun getListEventApi20() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(20).setPage(1)
        listEventRepository.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList.size, 20)
    }

    @Test
    fun getListEventApi50() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(50).setPage(2)
        listEventRepository.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList.size, 50)
    }

    @Test
    fun getListEventApi100() = runBlocking {
        var dataList = arrayListOf<Event>()
        val queryBuilder =
            EventsQueryBuilder().setPerPage(100).setPage(2)
        listEventRepository.getEventsApi(queryBuilder).collect {
            dataList = it
        }
        assertNotNull(dataList)
        assertEquals(dataList.size, 100)
    }
}