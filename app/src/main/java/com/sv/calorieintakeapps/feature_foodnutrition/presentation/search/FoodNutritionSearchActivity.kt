package com.sv.calorieintakeapps.feature_foodnutrition.presentation.search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.sv.calorieintakeapps.databinding.ActivityFoodNutritionSearchBinding
import com.sv.calorieintakeapps.feature_foodnutrition.data.OnClickItemMode
import com.sv.calorieintakeapps.feature_foodnutrition.di.FoodNutritionModule
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_FOOD_ID
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_FOOD_NAME
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_MERCHANT_ID
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CALORIES
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_CARBS
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_FAT
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_FOOD_ID
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_NILAIGIZI_COM_PROTEIN
import com.sv.calorieintakeapps.library_common.action.Actions.EXTRA_ON_CLICK_ITEM_MODE
import com.sv.calorieintakeapps.library_common.action.Actions.openFoodNutritionDetailsIntent
import com.sv.calorieintakeapps.library_common.action.Actions.openReportingIntent
import com.sv.calorieintakeapps.library_common.util.hideKeyboard
import com.sv.calorieintakeapps.library_common.util.load
import com.sv.calorieintakeapps.library_common.util.shouldVisible
import com.sv.calorieintakeapps.library_common.util.showKeyboard
import com.sv.calorieintakeapps.library_common.util.showToast
import com.sv.calorieintakeapps.library_database.ui.adapter.FoodNutritionAdapter
import com.sv.calorieintakeapps.library_database.vo.Resource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FoodNutritionSearchActivity : AppCompatActivity() {
    
    private val viewModel: FoodNutritionSearchViewModel by viewModel()
    
    private lateinit var binding: ActivityFoodNutritionSearchBinding
    private lateinit var foodNutritionAdapter: FoodNutritionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodNutritionSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        FoodNutritionModule.load()
        
        val merchantId = intent.getIntExtra(EXTRA_MERCHANT_ID, -1)
        val onClickItemMode = intent.getStringExtra(EXTRA_ON_CLICK_ITEM_MODE)?.let { OnClickItemMode.valueOf(it) }

        binding.apply {
            foodNutritionAdapter = FoodNutritionAdapter(merchantId) { foodId, merchantId ->
                when(onClickItemMode) {
                    OnClickItemMode.OPEN_DETAIL -> {
                        startActivity(openFoodNutritionDetailsIntent(foodId, merchantId))
                    }
                    OnClickItemMode.RETURN_DATA -> {
                        viewModel.setFoodId(foodId)
                        val returnIntent = Intent().apply {
                            putExtra(EXTRA_FOOD_ID, foodId)
                            putExtra(EXTRA_MERCHANT_ID, merchantId)
                        }
                        observeFoodNutritionDetails(returnIntent) {
                            finish()
                        }
                    }
                    null -> {}
                }
            }
                foodNutritionAdapter.apply {
                addLoadStateListener { loadState ->
                    if (loadState.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached) {
                        if (itemCount < 1) {
                            showToast("Data tidak ditemukan, coba gunakan kata kunci lain atau tambah baru")
                        }
                        btnAddNewFood.shouldVisible(itemCount < 1)
                    } else if (loadState.refresh is LoadState.Error) {
                        viewModel.nilaigiziComLogin()
                        showToast("Terjadi kesalahan, mohon coba lagi")
                    }
                }
            }
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@FoodNutritionSearchActivity)
                adapter = foodNutritionAdapter
            }
            
            searchView.showKeyboard()
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if ((query?.length ?: 0) > 1) {
                        lifecycleScope.launch {
                            viewModel.search(query!!).collectLatest { pagingData ->
                                foodNutritionAdapter.submitData(pagingData)
                            }
                        }
                        searchView.hideKeyboard()
                    } else {
                        showToast("Masukkan minimal 2 karakter")
                    }
                    return true
                }
                
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
            
            btnAddNewFood.setOnClickListener {
                startActivity(
                    openReportingIntent(
                        null,
                        null,
                        if (merchantId < 0) null else merchantId,
                        null,
                    )
                )
            }
            
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun observeFoodNutritionDetails(returnIntent: Intent, onDataReceived: () -> Unit) {
        viewModel.foodNutrition.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        result.data?.let { food ->
                            returnIntent
                                .putExtra(EXTRA_FOOD_NAME, food.name)
                                .putExtra(EXTRA_NILAIGIZI_COM_FOOD_ID, food.foodId)
                                .putExtra(EXTRA_NILAIGIZI_COM_CALORIES, food.calories)
                                .putExtra(EXTRA_NILAIGIZI_COM_PROTEIN, food.protein)
                                .putExtra(EXTRA_NILAIGIZI_COM_FAT, food.fat)
                                .putExtra(EXTRA_NILAIGIZI_COM_CARBS, food.carbs)
                        }
                        setResult(RESULT_OK, returnIntent)
                        onDataReceived.invoke()
                    }

                    is Resource.Error -> {
                        showToast(result.message)
                    }
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        FoodNutritionModule.unload()
    }
    
}