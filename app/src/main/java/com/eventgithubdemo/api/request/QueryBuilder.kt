package com.eventgithubdemo.api.request

abstract class QueryBuilder {

    fun build(): Map<String, String> {
        val queryParams = HashMap<String, String>()
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

}
