package com.sv.calorieintakeapps.library_database.data.source.local

import com.sv.calorieintakeapps.library_database.data.source.local.persistence.LoginSessionPreference

class LocalDataSource(private val loginSessionPreference: LoginSessionPreference) {

    fun storeLoginSession(userId: Int, userName: String) {
        loginSessionPreference.userId = userId
        loginSessionPreference.userName = userName
    }

    fun getUserId(): Int {
        return loginSessionPreference.userId
    }

    fun getUserName(): String {
        return loginSessionPreference.userName
    }

    fun logout() {
        loginSessionPreference.clear()
    }
}