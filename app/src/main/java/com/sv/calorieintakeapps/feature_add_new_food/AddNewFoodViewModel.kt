package com.sv.calorieintakeapps.feature_add_new_food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.collectLatest

class AddNewFoodViewModel(
    private val remoteDataSource: RemoteDataSource,
) : ViewModel() {

    private val newFood = MutableLiveData<NewFood>()

    fun addNewFood(newFood: NewFood) {
        this.newFood.value = newFood
    }

    val addNewFoodResult: LiveData<ApiResponse<String>> =
        newFood.switchMap {
            liveData {
                val result = remoteDataSource.addNewFood(it)
                result.collectLatest {
                    emit(it)
                }
            }
        }
    
}

data class NewFood(
    val name: String,
    val calories: String,
    val carbs: String,
    val protein: String,
    val fat: String,
    val water: String,
)