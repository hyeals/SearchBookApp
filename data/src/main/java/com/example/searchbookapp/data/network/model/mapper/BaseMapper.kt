package com.example.searchbookapp.data.network.model.mapper

import com.example.searchbookapp.data.network.model.ApiResponse
import com.example.searchbookapp.data.network.model.ApiResult
import com.example.searchbookapp.domain.model.EntityWrapper

abstract class BaseMapper<M, E> {

    fun mapFromResult(result: ApiResult<M>, extra: Any? = null): EntityWrapper<E> =
        when (result.response) {
            is ApiResponse.Success -> getSuccess(model = result.response.data, extra = extra)
            is ApiResponse.Fail -> getFailure(error = result.response.error)
        }

    abstract fun getSuccess(model: M?, extra: Any?): EntityWrapper.Success<E>

    abstract fun getFailure(error: Throwable): EntityWrapper.Fail<E>
}