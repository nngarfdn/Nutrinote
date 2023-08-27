package com.sv.calorieintakeapps.feature_fooddetails.presentation

import androidx.lifecycle.*
import com.sv.calorieintakeapps.feature_fooddetails.domain.usecase.FoodDetailsUseCase
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrient
import com.sv.calorieintakeapps.library_database.vo.Resource

class FoodDetailViewModel(private val foodDetailsUseCase: FoodDetailsUseCase) : ViewModel() {

    private val foodId = MutableLiveData<Int>()

    fun setFoodId(foodId: Int) {
        this.foodId.value = foodId
    }


    val foodNutrients: LiveData<Resource<List<FoodNutrient>>> =
        Transformations.switchMap(foodId) {
            foodDetailsUseCase.getFoodNutrientsById(it).asLiveData()
        }
}