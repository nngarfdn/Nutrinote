package com.sv.calorieintakeapps.library_database.data.source.local

import com.sv.calorieintakeapps.library_database.data.source.local.persistence.LoginSessionPreference

class LocalDataSource(private val loginSessionPreference: LoginSessionPreference) {
    
    fun storeLoginSession(userId: Int, userName: String) {
        loginSessionPreference.userId = userId
        loginSessionPreference.userName = userName
    }
    
    fun storeToken(token: String) {
        loginSessionPreference.token = token
    }
    
    fun getUserId(): Int {
        return loginSessionPreference.userId
    }
    
    fun getUserName(): String {
        return loginSessionPreference.userName
    }
    
    fun getToken(): String {
        return loginSessionPreference.token
    }
    
    fun logout() {
        loginSessionPreference.clear()
    }
    
}