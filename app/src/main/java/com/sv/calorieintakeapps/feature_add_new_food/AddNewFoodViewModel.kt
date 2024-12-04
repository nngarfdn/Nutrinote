package com.sv.calorieintakeapps.feature_add_new_food

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.sv.calorieintakeapps.library_database.data.source.remote.RemoteDataSource
import com.sv.calorieintakeapps.library_database.vo.ApiResponse
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

    private val listNewUrt = MutableLiveData<List<NewUrt>>()

    fun addNewUrt(listNewUrt: List<NewUrt>) {
        this.listNewUrt.value = listNewUrt
    }

    val addNewUrtResult: LiveData<ApiResponse<List<String>>> =
        listNewUrt.switchMap {
            liveData {
                val result = remoteDataSource.addNewUrt(it)
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
    val urtTersedia: String,
)

@JsonClass(generateAdapter = true)
data class NewUrt(
    @Json(name = "nama_urt") val name: String,
    @Json(name = "gram_ml_per_porsi") val gramOrMlPerPortion: String,
)