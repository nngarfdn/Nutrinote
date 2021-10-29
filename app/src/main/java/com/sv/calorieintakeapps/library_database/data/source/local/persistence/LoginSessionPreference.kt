package com.sv.calorieintakeapps.library_database.data.source.local.persistence

import android.content.Context

private const val PREFERENCE_NAME = "login_session"
private const val USER_ID = "user_id"
private const val USER_NAME = "user_name"

class LoginSessionPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    internal var userId: Int
        get() = preferences.getInt(USER_ID, -1)
        set(value) = preferences.edit().putInt(USER_ID, value).apply()

    internal var userName: String
        get() = preferences.getString(USER_NAME, "").orEmpty()
        set(value) = preferences.edit().putString(USER_NAME, value).apply()

    internal fun clear() {
        preferences.edit().clear().apply()
    }
}