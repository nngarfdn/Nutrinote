package com.sv.calorieintakeapps.library_database.helper

import com.squareup.moshi.Moshi
import okio.IOException
import retrofit2.HttpException

internal fun parseErrorMessage(throwable: Throwable): String {
    throwable.printStackTrace()
    return when (throwable) {
        is IOException -> throwable.message.orEmpty()
        is HttpException -> {
            val errorResponse = convertErrorBody(throwable)
            errorResponse?.apiMessage
                ?: errorResponse?.getSecondaryApiMessage()
                ?: throwable.message.orEmpty()
        }
        
        else -> throwable.message ?: throwable.localizedMessage ?: throwable.toString()
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