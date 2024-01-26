package com.example.searchbookapp.data.network.api

import com.example.searchbookapp.data.network.model.ApiResult
import com.example.searchbookapp.data.network.model.NetworkRequestInfo
import java.lang.reflect.Type

interface NetworkRequestFactory {
    suspend fun <T> create(
        url: String,
        requestInfo: NetworkRequestInfo = NetworkRequestInfo.Builder().build(),
        type: Type
    ): ApiResult<T>
}