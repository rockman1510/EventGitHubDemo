package com.eventgithubdemo.utils.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewUtils {
    fun isLastItemVisible(recyclerView: RecyclerView, itemCount: Int): Boolean {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        return pos >= itemCount - 1
    }

    fun isFirstItemVisible(recyclerView: RecyclerView): Boolean{
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val first = layoutManager.findFirstVisibleItemPosition()
        return first == 0
    }
}