package com.eventgithubdemo

import com.eventgithubdemo.api.model.Event


object UnitTestUtils {
    fun generateListEvent(
        perPage: Int
    ): ArrayList<Event> {
        val data = arrayListOf<Event>()
        for (i in 1 until (perPage + 1)) {
            data.add(Event("id $i", "type $i", null, null, true, "created_at $i", null))
        }
        return data
    }
}