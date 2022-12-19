package com.eventgithubdemo.ui.event

import android.os.Handler
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eventgithubdemo.MainCoroutineRule
import com.eventgithubdemo.UnitTestUtils
import com.eventgithubdemo.base.CoroutinesDispatcherProvider
import com.eventgithubdemo.getOrAwaitValue
import com.eventgithubdemo.repository.FakeListEventRepositoryImpl
import com.eventgithubdemo.ui.domain.FakeGetListEventUseCase
import com.eventgithubdemo.ui.event.mvi.EventsState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


@ExperimentalCoroutinesApi
class ListEventViewModelTest {
    @get:Rule
    private val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dispatcherProvider: CoroutinesDispatcherProvider
    private lateinit var listEventViewModel: ListEventViewModel

    @Before
    fun setUp() {
        dispatcherProvider = CoroutinesDispatcherProvider(
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher,
            mainCoroutineRule.dispatcher
        )
    }

    @Test
    fun `processIntent - 5`() = runBlocking {
        val handler = mock(Handler::class.java)
        val fakeGetListEventUseCase = FakeGetListEventUseCase(FakeListEventRepositoryImpl(
            UnitTestUtils.generateListEvent(5), dispatcherProvider), dispatcherProvider)
        listEventViewModel = ListEventViewModel(
                fakeGetListEventUseCase, dispatcherProvider, handler
        )
        val state = listEventViewModel.getState().getOrAwaitValue()
        assert(state is EventsState.OnSuccess)
        assertEquals((state as EventsState.OnSuccess).dataList.size, 5)
    }

    @Test
    fun `processIntent - 20`() = runBlocking {
        val handler = mock(Handler::class.java)
        val fakeGetListEventUseCase = FakeGetListEventUseCase(FakeListEventRepositoryImpl(
            UnitTestUtils.generateListEvent(20), dispatcherProvider), dispatcherProvider)
        listEventViewModel = ListEventViewModel(
            fakeGetListEventUseCase, dispatcherProvider, handler
        )
        val state = listEventViewModel.getState().getOrAwaitValue()
        assert(state is EventsState.OnSuccess)
        assertEquals((state as EventsState.OnSuccess).dataList.size, 20)
    }

    @Test
    fun `processIntent - 50`() = runBlocking {
        val handler = mock(Handler::class.java)
        val fakeGetListEventUseCase = FakeGetListEventUseCase(FakeListEventRepositoryImpl(
            UnitTestUtils.generateListEvent(50), dispatcherProvider), dispatcherProvider)
        listEventViewModel = ListEventViewModel(
            fakeGetListEventUseCase, dispatcherProvider, handler
        )
        val state = listEventViewModel.getState().getOrAwaitValue()
        assert(state is EventsState.OnSuccess)
        assertEquals((state as EventsState.OnSuccess).dataList.size, 50)
    }

}