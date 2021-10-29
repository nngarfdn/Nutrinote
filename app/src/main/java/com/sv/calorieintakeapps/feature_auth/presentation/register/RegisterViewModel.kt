package com.sv.calorieintakeapps.feature_auth.presentation.register

import androidx.lifecycle.*
import com.sv.calorieintakeapps.feature_auth.domain.usecase.AuthUseCase
import com.sv.calorieintakeapps.library_database.domain.model.User
import com.sv.calorieintakeapps.library_database.vo.Resource

class RegisterViewModel(private val authUseCase: AuthUseCase) : ViewModel() {

    private val user = MutableLiveData<User>()

    fun register(
        name: String,
        email: String,
        password: String,
    ) {
        user.value = User(
            name = name,
            email = email,
            password = password
        )
    }

    val registerResult: LiveData<Resource<Boolean>> =
        Transformations.switchMap(user) {
            authUseCase.register(it.name, it.email, it.password, it.password).asLiveData()
        }
}