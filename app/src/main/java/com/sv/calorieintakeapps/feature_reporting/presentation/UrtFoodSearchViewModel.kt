package com.sv.calorieintakeapps.feature_reporting.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.sv.calorieintakeapps.feature_foodnutrition.domain.usecase.FoodNutritionUseCase
import com.sv.calorieintakeapps.library_database.data.source.local.LocalDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFood
import com.sv.calorieintakeapps.library_database.data.source.remote.urt.UrtFoodDetail
import com.sv.calorieintakeapps.library_database.domain.model.FoodNutrition
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UrtFoodSearchViewModel(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
) : ViewModel() {

    fun setFoodNameQuery(foodName: String) {
        this.foodName.value = foodName
    }

    private var foodName = MutableLiveData<String>()

    val searchResult: LiveData<ApiResponse<List<UrtFood>>> = foodName.switchMap {
        liveData {
            remoteDataSource.searchUrtFood(it).collectLatest {
                emit(it)
            }
        }
    }

    fun setFoodDetailId(foodDetailId: Int) {
        this.foodDetailId.value = foodDetailId
    }

    private var foodDetailId = MutableLiveData<Int>()

    val foodDetailResult: LiveData<ApiResponse<UrtFoodDetail>> = foodDetailId.switchMap {
        liveData {
            remoteDataSource.getUrtFoodDetail(it).collectLatest {
                emit(it)
            }
        }
    }
}