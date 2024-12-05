package com.sv.calorieintakeapps.library_database.helper

import androidx.lifecycle.LiveData
import com.sv.calorieintakeapps.library_database.vo.Resource

fun <T> LiveData<Resource<T>>.onLoading(action: () -> Unit): LiveData<Resource<T>> {
    observeForever { result ->
        if (result is Resource.Loading) {
            action()
        }
    }
    return this
}

fun <T> LiveData<Resource<T>>.onSuccess(action: (T) -> Unit): LiveData<Resource<T>> {
    observeForever { result ->
        if (result is Resource.Success) {
            result.data?.let(action)
        }
    }
    return this
}

fun <T> LiveData<Resource<T>>.onError(action: (String) -> Unit): LiveData<Resource<T>> {
    observeForever { result ->
        if (result is Resource.Error) {
            result.message?.let(action)
        }
    }
    return this
}