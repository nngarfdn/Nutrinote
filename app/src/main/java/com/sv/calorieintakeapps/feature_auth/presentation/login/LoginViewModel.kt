package com.sv.calorieintakeapps.feature_auth.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.sv.calorieintakeapps.feature_auth.domain.usecase.AuthUseCase
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginViewModel(private val authUseCase: AuthUseCase) : ViewModel() {
    
    private val user = MutableLiveData<User>()
    
    fun login(email: String, password: String) {
        user.value = User(
            email = email,
            password = password,
        )
    }
    
    val loginResult: LiveData<Resource<Boolean>> =
        user.switchMap {
            authUseCase.login(it.email, it.password).asLiveData()
        }
    
    fun nilaigiziComLogin() {
        viewModelScope.launch {
            authUseCase.nilaigiziComLogin().collect()
        }
    }
    
}