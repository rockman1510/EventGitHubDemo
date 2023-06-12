package com.eventgithubdemo.ui.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.eventgithubdemo.R
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.databinding.FragmentListEventBinding
import com.eventgithubdemo.design_pattern.observer.MyTopic
import com.eventgithubdemo.design_pattern.observer.MyTopicSubscriber
import com.eventgithubdemo.design_pattern.observer.Observer
import com.eventgithubdemo.design_pattern.proxy.CommandExecutorProxy
import com.eventgithubdemo.ui.EventSelectListener
import com.eventgithubdemo.ui.event.mvi.EventsIntent
import com.eventgithubdemo.ui.event.mvi.EventsState
import com.eventgithubdemo.utils.view.RecyclerViewUtils.isFirstItemVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListEventFragment : Fragment(), EventSelectListener {

    private lateinit var viewModel: ListEventViewModel

    private lateinit var binding: FragmentListEventBinding

    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_event, container, false)
        viewModel = ViewModelProvider(this)[ListEventViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launchWhenResumed {
            viewModel.getState().observe(viewLifecycleOwner, ::onCallBackState)
        }
    }

    override fun onStop() {
        super.onStop()
        saveStateAction()
    }

    private fun saveStateAction() {
        eventAdapter.let {
            binding.rvEvent.scrollToPosition(it.itemCount - 5)
            viewModel.processIntent(EventsIntent.SaveSuccessState)
        }
    }

    private fun initViews() {
        binding.rvEvent.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        eventAdapter = EventAdapter(this)
        binding.adapter = eventAdapter
        binding.swipeLayout.setOnRefreshListener {
            viewModel.processIntent(EventsIntent.FetchData(false))
        }
    }

    private fun onCallBackState(state: EventsState) {
        when (state) {
            is EventsState.OnLoading -> {
                testProxyPattern()
                testObserverPattern()
            }
            is EventsState.OnSuccess -> {
                binding.swipeLayout.isRefreshing = false
//                val isFirstPosition = RecyclerViewUtils.isFirstItemVisible(binding.rvEvent)
                val isFirstPosition = binding.rvEvent.isFirstItemVisible()
                eventAdapter.submitList(state.dataList.toMutableList())
                if (isFirstPosition) {
                    binding.rvEvent.smoothScrollToPosition(0)
                }
            }
            is EventsState.OnError -> {
                binding.swipeLayout.isRefreshing = false
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun testProxyPattern() {
        Log.d("HuyLV", "//--------------------------------------------//")
        val commander1 = CommandExecutorProxy("HuyLV", "levanhuy")
        val commander2 = CommandExecutorProxy("HuyLe", "this is a test")
        commander1.runCommand("open the door!")
        commander1.runCommand("rm user test!")
        commander2.runCommand("rm user test2")
        commander2.runCommand("read log files")
    }

    private fun testObserverPattern() {
        Log.d("HuyLV", "//--------------------------------------------//")
        val topic = MyTopic()
        val observer1: Observer = MyTopicSubscriber("Observer1")
        val observer2: Observer = MyTopicSubscriber("Observer2")
        val observer3: Observer = MyTopicSubscriber("Observer3")

        topic.register(observer1)
        topic.register(observer2)
        topic.register(observer3)

        observer1.setObservable(topic)
        observer2.setObservable(topic)
        observer3.setObservable(topic)

        observer1.update()
        observer2.update()
        observer3.update()

        topic.postMessage("New message 1")
        topic.unRegister(observer2)
        lifecycleScope.launchWhenResumed {
            withContext(Dispatchers.IO) {
                launch {
                    delay(3000L)
                    topic.postMessage("New message 2")
                }.join()
            }
        }
    }

    override fun onEventSelected(event: Event) =
        findNavController().navigate(ListEventFragmentDirections.goToDetail(event))
}