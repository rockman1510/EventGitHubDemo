package com.eventgithubdemo.api.request

import com.eventgithubdemo.constant.Constants


class EventsQueryBuilder : QueryBuilder() {
    private var perPage: Int? = 5
    private var page: Int? = 1

    fun setPerPage(perPage: Int = 5): EventsQueryBuilder {
        this.perPage = perPage
        return this
    }

    fun setPage(page: Int = 1): EventsQueryBuilder {
        this.page = page
        return this
    }

    override fun putQueryParams(queryParams: MutableMap<String, String>) {
        perPage?.apply { queryParams[Constants.QUERY_PER_PAGE] = "$this" }
        page?.apply { queryParams[Constants.QUERY_PAGE] = "$this" }
    }
}
