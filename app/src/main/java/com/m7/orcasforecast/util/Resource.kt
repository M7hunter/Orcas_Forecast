package com.m7.orcasforecast.util

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val msg: String? = null,
    val redId: Int? = null
) {
    companion object {
        fun <T> loading(data: T? = null): Resource<T> = Resource(Status.LOADING, data)
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data)
        fun <T> error(data: T? = null, msg: String? = null, resId: Int? = null): Resource<T> =
            Resource(Status.ERROR, data, msg, resId)
    }
}
