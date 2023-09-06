package com.sv.calorieintakeapps.library_database.data.source.local.persistence

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val PREFERENCE_NAME = "login_session"
private const val USER_ID = "user_id"
private const val USER_NAME = "user_name"
private const val TOKEN = "token"

class LoginSessionPreference(context: Context) {
    
    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    
    private val preferences = EncryptedSharedPreferences.create(
        PREFERENCE_NAME,
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    internal var userId: Int
        get() = preferences.getInt(USER_ID, -1)
        set(value) = preferences.edit().putInt(USER_ID, value).apply()
    
    internal var userName: String
        get() = preferences.getString(USER_NAME, "").orEmpty()
        set(value) = preferences.edit().putString(USER_NAME, value).apply()
    
    internal var token: String
        get() = preferences.getString(TOKEN, "").orEmpty()
        set(value) = preferences.edit().putString(TOKEN, value).apply()
    
    internal fun clear() {
        preferences.edit().clear().apply()
    }
    
}