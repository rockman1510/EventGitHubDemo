package com.eventgithubdemo.ui.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eventgithubdemo.R
import com.eventgithubdemo.api.model.Event
import com.eventgithubdemo.databinding.LayoutEventItemsBinding
import com.eventgithubdemo.ui.EventSelectListener

class EventAdapter(
    private val eventSelectListener: EventSelectListener,
) : ListAdapter<Event, EventAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutEventItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.layout_event_items, parent, false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), eventSelectListener)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ViewHolder(private val binding: LayoutEventItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event, selectListener: EventSelectListener) {
            binding.event = event
            binding.selectListener = selectListener
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }

    }
}
