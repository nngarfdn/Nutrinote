package com.sv.calorieintakeapps.library_database.helper

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sv.calorieintakeapps.library_database.data.source.remote.main.response.RegisterResponse

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    
    @Json(name = "api_status")
    val apiStatus: Int? = null,
    
    @Json(name = "api_message")
    val apiMessage: String? = null,
    
    // Only in register response
    @Json(name = "message")
    val secondaryApiMessage: String? = null,
    
    @Json(name = "errors")
    val errors: RegisterResponse.Errors? = null,
    
    ) {
    
    @JvmName("getSecondaryApiMessage1")
    fun getSecondaryApiMessage(): String {
        val message: StringBuilder = StringBuilder().append(secondaryApiMessage)
        if (errors?.email?.isNotEmpty() == true) message.appendLine(errors.email)
        if (errors?.name?.isNotEmpty() == true) message.appendLine(errors.name)
        if (errors?.password?.isNotEmpty() == true) message.appendLine(errors.password)
        return message.toString()
    }
    
}