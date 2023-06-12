package com.eventgithubdemo.utils.view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewUtils {
//    fun isLastItemVisible(recyclerView: RecyclerView, itemCount: Int): Boolean {
//        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
//        return pos >= itemCount - 1
//    }
//
//    fun isFirstItemVisible(recyclerView: RecyclerView): Boolean {
//        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//        val first = layoutManager.findFirstVisibleItemPosition()
//        return first == 0
//    }

    fun RecyclerView.isFirstItemVisible(): Boolean {
        return (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition() == 0
    }

    fun RecyclerView.isLastItemVisible(itemCount: Int): Boolean{
        val pos = (layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
        return pos >= itemCount - 1
    }
}