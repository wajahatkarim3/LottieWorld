package com.wajahatkarim3.lottieworld.data

import com.wajahatkarim3.lottieworld.data.remote.interceptors.NoInternetException
import java.io.IOException

sealed class DataResource<out R> {
    data class Success<out T>(val data: T) : DataResource<T>()
    object Empty : DataResource<Nothing>()
    data class Error<out T>(val exception: Throwable) : DataResource<Nothing>()
    object Loading : DataResource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error<*> -> "Error[exception=$exception]"
            is Empty -> "Empty"
            Loading -> "Loading"
        }
    }
}

fun <T> DataResource<T>.successOr(fallback: T): T {
    return (this as? DataResource.Success<T>)?.data ?: fallback
}

fun <T> DataResource<T>.succeeded(): Boolean {
    return this is DataResource.Success<T>
}

val <T> DataResource<T>.data: T?
    get() = (this as? DataResource.Success<T>)?.data

fun <T> T.isEmptyResponse(): Boolean {
    return this != null && this is List<*> && this.isEmpty()
}

suspend fun <T> callApi(apiCall: suspend () -> T): DataResource<T> {
    return try {
        val response = apiCall.invoke()
        if (response.isEmptyResponse()) {
            DataResource.Empty
        } else {
            DataResource.Success(response)
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> DataResource.Error<Any>(NoInternetException())
            else -> DataResource.Error<Any>(throwable)
        }
    }
}

fun <T> DataResource<T>.onSuccess(
    onResult: DataResource.Success<T>.() -> Unit
): DataResource<T> {
    if (this is DataResource.Success) {
        onResult(this)
    }
    return this
}

fun <T> DataResource<T>.onError(
    onResult: DataResource.Error<T>.() -> Unit
): DataResource<T> {
    if (this is DataResource.Error<*>) {
        onResult(this as DataResource.Error<T>)
    }
    return this
}

fun <T> DataResource<T>.onEmpty(
    onResult: DataResource.Empty.() -> Unit
): DataResource<T> {
    if (this is DataResource.Empty) {
        onResult(this)
    }
    return this
}

fun <T> DataResource<T>.onLoading(
    onResult: DataResource.Loading.() -> Unit
): DataResource<T> {
    if (this is DataResource.Loading) {
        onResult(this)
    }
    return this
}