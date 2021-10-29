package com.sv.calorieintakeapps.library_database.helper

import com.squareup.moshi.Moshi
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber

internal fun parseErrorMessage(throwable: Throwable): String {
    Timber.tag(RemoteDataSource::class.java.simpleName).e(throwable.toString())
    return when (throwable) {
        is IOException -> throwable.message.orEmpty()
        is HttpException -> {
            val errorResponse = convertErrorBody(throwable)
            errorResponse?.apiMessage
                ?: errorResponse?.getSecondaryApiMessage()
                ?: throwable.message.orEmpty()
        }
        else -> throwable.message.orEmpty()
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.source()?.let {
            val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
            moshiAdapter.fromJson(it)
        }
    } catch (exception: Exception) {
        null
    }
}