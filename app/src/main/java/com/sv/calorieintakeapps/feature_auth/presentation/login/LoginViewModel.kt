package com.sv.calorieintakeapps.feature_auth.presentation.login

import androidx.lifecycle.*
import com.sv.calorieintakeapps.feature_auth.domain.usecase.AuthUseCase
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource

class LoginViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val user = MutableLiveData<User>()

    fun login(email: String, password: String) {
        user.value = User(
            email = email,
            password = password,
        )
    }

    val loginResult: LiveData<Resource<Boolean>> =
        Transformations.switchMap(user) {
            authUseCase.login(it.email, it.password).asLiveData()
        }
}